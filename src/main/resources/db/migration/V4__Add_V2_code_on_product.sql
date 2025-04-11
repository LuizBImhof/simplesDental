ALTER TABLE products ADD COLUMN code_v2 BIGINT;

ALTER TABLE products ADD CONSTRAINT unique_product_code_v2 UNIQUE (code_v2);