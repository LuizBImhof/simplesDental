-- Índices para a tabela products
CREATE INDEX idx_products_name ON products(name);
CREATE INDEX idx_products_category_id ON products(category_id);

-- Índices para a tabela categories
CREATE INDEX idx_categories_name ON categories(name);

-- Restrições de unicidade
ALTER TABLE products ADD CONSTRAINT unique_product_code UNIQUE (code);
ALTER TABLE categories ADD CONSTRAINT unique_category_name UNIQUE (name);
