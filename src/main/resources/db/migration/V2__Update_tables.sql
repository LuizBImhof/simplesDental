ALTER TABLE categories
    ALTER COLUMN name TYPE VARCHAR(100),
    ALTER COLUMN description TYPE VARCHAR(255);


ALTER TABLE products
    ALTER COLUMN name TYPE VARCHAR(100),
    ADD CONSTRAINT name_not_empty CHECK (char_length(trim(name)) > 0),

    ALTER COLUMN description TYPE VARCHAR(255),

    ALTER COLUMN price SET NOT NULL,
    ADD CONSTRAINT price_positive CHECK (price > 0),

    ALTER COLUMN status SET NOT NULL,
    ALTER COLUMN category_id SET NOT NULL;
