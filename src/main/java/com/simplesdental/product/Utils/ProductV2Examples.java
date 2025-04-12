package com.simplesdental.product.Utils;

public class ProductV2Examples {

    public static final String CREATE_UPDATE_EXAMPLE = """
            {
              "name": "Smartphone XYZ",
              "description": "Smartphone com 8GB RAM e 128GB de armazenamento",
              "price": 1299.99,
              "status": true,
              "category": {
                "id": 3,
                "name": "Smartphones",
                "description": "Telefones celulares e acessórios"
              },
              "code": 1
            }
            """;

    public static final String PAGE_RETURN_EXAMPLE = """
            {
              "content": [
                {
                  "id": 6,
                  "name": "Cadeira Ergonômica",
                  "description": "Cadeira ergonômica para escritório",
                  "price": 899.99,
                  "status": true,
                  "category": {
                    "id": 5,
                    "name": "Móveis",
                    "description": "Móveis para escritório e casa"
                  },
                  "code": 2
                },
                {
                  "id": 9,
                  "name": "Fone de Ouvido Bluetooth",
                  "description": "Fone de ouvido sem fio com cancelamento de ruído",
                  "price": 399.99,
                  "status": true,
                  "category": {
                    "id": 1,
                    "name": "Eletrônicos",
                    "description": "Produtos eletrônicos e gadgets"
                  },
                  "code": 3
                }
              ],
              "pageable": {
                "pageNumber": 0,
                "pageSize": 2,
                "sort": {
                  "empty": false,
                  "sorted": true,
                  "unsorted": false
                },
                "offset": 0,
                "paged": true,
                "unpaged": false
              },
              "totalPages": 6,
              "totalElements": 11,
              "last": false,
              "size": 2,
              "number": 0,
              "sort": {
                "empty": false,
                "sorted": true,
                "unsorted": false
              },
              "numberOfElements": 2,
              "first": true,
              "empty": false
            }
            """;

    public static final String RETURN_SINGLE_PRODUCT_EXAMPLE = """
                {
                  "id": 1,
                  "name": "Notebook ABC",
                  "description": "Notebook com processador i7",
                  "price": 3999.99,
                  "status": true,
                  "code": 1,
                  "category": {
                    "id": 2,
                    "name": "Informática",
                    "description": "Produtos para computadores e acessórios"
                  }
                }
            """;

}
