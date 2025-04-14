
## Respostas

### 1. Se tivesse a oportunidade de criar o projeto do zero ou refatorar o projeto atual, qual arquitetura você utilizaria e por quê?
Uma boa alternativa para o projeto, caso ele fosse crescer bastante, seria a **arquitetura hexagonal**, pois ela separa as regras do negócio da infraestrutura e torna o sistema bem desacoplado e testável com maior facilidade, além de facilitar a migração futuramente para o uso de microsserviços, por exemplo. Com essa separação de camadas, também fica mais fácil alterar alguma tecnologia utilizada no projeto, como migrar para um banco não relacional ou até mesmo se a comunicação com o exterior do projeto mudar de uma REST API para outra especificação. 
Claro que isso traz mais complexidade no desenvolvimento, sendo necessário um maior cuidado no desenvolvimento. A solução atual acaba sendo uma opção válida e bem robusta para um projeto de pequeno a médio porte.

### 2. Qual é a melhor estratégia para garantir a escalabilidade do código mantendo o projeto organizado?
Eu acredito que a organização de pastas do projeto do jeito que está acaba ficando muito confusa quando aumentar muito de tamanho. Eu separaria por domínios, por exemplo, um pacote do Produto, que teria todas as classes relacionadas a ele: o Controller, o Service, o Repository etc.
Além disso, para melhor escalabilidade, eu faria o uso de DTOs para a transmissão de dados entre as camadas, ao invés de usar a própria entidade, como está sendo feito no momento. Inclusive, na autenticação, já fiz desse jeito, mas não tive tempo de alterar o restante do projeto, e seria mais uma melhoria futura mesmo.
Acho que o modelo que fiz para a especificação dos endpoints no Swagger foi bom, porém fiz apenas em um controller para exemplificar. Seria interessante para o projeto aplicar em todos, trazer mais exemplos e, com o tratamento de exceção correto, melhorar as mensagens de erro no exemplo também.

### 3. Quais estratégias poderiam ser utilizadas para implementar multitenancy no projeto?
São basicamente três estratégias:
- **Tenant ID em cada entidade:** adicionaríamos um `tenant-id` em cada entidade, extrairíamos esse dado na chamada da API (podendo ser via header, mas pelo que pesquisei, pode ser feito com o JWT também). Então, com esse `tenant-id`, adicionaríamos em cada consulta ao banco um adicional na cláusula `WHERE` para filtrar por ele.
- **Um banco de dados por tenant:** a aplicação gerenciaria múltiplas conexões para cada um desses bancos. A vantagem é que cada cliente pode ficar responsável por disponibilizar o banco para a aplicação, porém temos a complexidade de lidar com multiplas conexões aos bancos de dados.
- **Um schema por tenant:** nesse caso, seria necessário um banco que suporte múltiplos schemas, mas teríamos apenas um banco de dados.
Além dessas alternativas, tem também a possibilidade de cada cliente ter a própria infraestrutura completa, onde cada instância da aplicação serve apenas um cliente, mas acho que não vem ao caso, pois não seria multitenancy, e sim várias aplicações completas e totalmente isoladas.

### 4. Como garantir a resiliência e alta disponibilidade da API durante picos de tráfego e falhas de componentes?
- Subir mais de uma instância do aplicativo e usar um **load balancer** para distribuir o tráfego e permitir a continuação do uso caso uma das instâncias caia.
- Usar um **actuator** para monitorar o tráfego e a situação do sistema e combinar com um **restart automático** do sistema ao detectar algum problema.
- Usar **mensageria**, como o RabbitMQ, para processar as tarefas mais pesadas sem que travem o sistema.
- Usar **cache** para consultas feitas com mais frequência (por exemplo, o Redis para autenticação, como fazia parte do desafio).
- Além disso, é importante **testar a resiliência** do programa, simulando falhas reais para saber como o sistema lida com essas falhas e tratá-las da melhor maneira.

### 5. Quais práticas de segurança essenciais você implementaria para prevenir vulnerabilidades como injeção de SQL e XSS?
Quanto à prevenção da injeção de SQL, o uso do Hibernate já acaba resolvendo essa questão, desde que façamos o uso correto da ferramenta e não construamos as nossas queries SQL manualmente. Ainda assim, caso seja necessário criar alguma query mais específica, é preciso tomar cuidado para usar parâmetros e não criar a query com concatenação.
Já para a proteção contra XSS, é possível configurar para que seja usada a proteção de XSS nativa dos navegadores, e também validar os dados de entrada e **sanitizá-los** para não permitir padrões clássicos de ataque como `<script></script>`.

### 6. Qual a abordagem mais eficaz para estruturar o tratamento de exceções de negócio, garantindo um fluxo contínuo desde sua ocorrência até o retorno da API?
Seria a criação de **exceções específicas** para o projeto, usar o `@ControllerAdvice` e o `@ExceptionHandler` para tratar a exceção antes de retornar o erro para o client. Dessa maneira, é possível centralizar o tratamento de exceções e padronizar as mensagens de erro.

### 7. Considerando uma aplicação composta por múltiplos serviços, quais componentes você considera essenciais para assegurar sua robustez e eficiência?
Acho que a **segurança por token** é essencial. Ao aumentar a quantidade de serviços independentes, o uso de **mensageria** se torna de bastante importância também. Além disso, o uso de uma ferramenta para **logs** se torna muito útil para encontrar de maneira mais eficiente onde está acontecendo qualquer problema no sistema.

### 8. Como você estruturaria uma pipeline de CI/CD para automação de testes e deploy, assegurando entregas contínuas e confiáveis?
Apesar de eu não ter feito do início uma implementação de pipeline CI/CD, sei a ideia. Usando uma **trigger do Git**, como um Pull Request em alguma branch específica, devemos disparar a pipeline, que deve compilar o código e verificar se há erros, executar os testes automatizados — caso haja falha, paramos a pipeline.
Depois, usar ferramentas como o **SonarQube** para checar a cobertura de testes, code smells, códigos duplicados etc., ter um **checkstyle** para manter um padrão de formatação no código. Depois de tudo aprovado, pode ser gerada a **imagem Docker** e seguir com o deploy.
Daqui pra frente, não tenho muito conhecimento, pois nos locais que trabalhei isso sempre acabou sendo tarefa de outro time. Mas também sei a teoria de ter **ambientes de testes**, onde deve ser feito o deploy da aplicação (de preferência de forma automática) e, então, serem executados **testes mais complexos de end-to-end**. Só então seria feito o deploy na produção, onde podemos usar estratégias de não realizar o deploy em todas as instâncias de uma vez só, mas deixar algumas na versão anterior, até que tenhamos segurança total para aplicar o deploy em todas as instâncias da aplicação.
Para executar toda essa pipeline, a ferramenta mais conhecida deve ser o **Jenkins** ou o **GitHub Actions**.

## Observações quanto ao meu desenvolvimento do projeto
Deixei de fazer o **logging** e a implementação do **Redis para o context**, e o motivo foi porque ambas seriam a minha primeira vez implementando do zero. Optei por deixar para o final e acabei ficando sem tempo.
No caso do logging, já usei loggers simples, mas nunca desenvolvi do começo o uso de logging assíncrono e com retenção/compressão. Pesquisei quais ferramentas fazem isso e encontrei, por exemplo, o **Logback**, mas como falei antes, para fazer do zero eu levaria muito tempo e acabaria sendo apenas uma cópia de exemplos, sem muito entendimento do processo. Assim como o caso do Redis — seria muita cópia de projetos existentes.

Outra coisa que aconteceu foi com os **testes unitários após a implementação da autenticação**. Tive um problema onde, após o desenvolvimento do uso do bearer token, eu não estava conseguindo fazer os testes rodarem corretamente, especialmente em casos que apenas o role `ADMIN` permitia acesso ao endpoint. No final das contas, deixei a lógica dos testes no projeto, porém muitos não estão passando devido a esse problema.
Uma das situações é que, após essa implementação, todo erro estava retornando como `403` ou `401` e não mais com os códigos de erros como especificado anteriormente no teste. Também acabei não resolvendo essa questão por falta de tempo. Acho que parte dessa solução seria realizar o tratamento de exceção correto, usando o `@ControllerAdvice`, `@ExceptionHandler` e exceções personalizadas para o projeto.

Além disso, vocês podem ver que não fiz os testes em todos os endpoints e não fiz a descrição do swagger para todos os controllers, optei por isso para ter tempo de avançar em outras partes do projeto, mas como a estrutura já está lá, seria apenas passar a usar no restante do projeto, então pode se observar que eu consigo fazer,e apenas fiquei sem tempo de aplicar no restante.

Mas, de qualquer maneira, achei o desafio muito interessante — muito melhor do que o modelo padrão de testes técnicos, que é sempre iniciar uma API do zero. E independente do resultado do processo, pretendo continuar fazendo as modificações no projeto para aprender e desenvolver as partes que faltaram do desafio.
