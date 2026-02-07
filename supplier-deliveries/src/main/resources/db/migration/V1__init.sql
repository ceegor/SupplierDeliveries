CREATE TABLE suppliers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(20) NOT NULL
);

CREATE TABLE supplier_prices (
    id BIGSERIAL PRIMARY KEY,
    supplier_id BIGINT NOT NULL REFERENCES suppliers(id),
    product_id BIGINT NOT NULL REFERENCES products(id),
    valid_from DATE NOT NULL,
    valid_to DATE NOT NULL,
    price_per_kg NUMERIC(12, 2) NOT NULL,
    CONSTRAINT chk_price_period CHECK (valid_to >= valid_from)
);

CREATE INDEX idx_supplier_prices_lookup
ON supplier_prices (supplier_id, product_id, valid_from, valid_to);

CREATE TABLE supplies (
    id BIGSERIAL PRIMARY KEY,
    supplier_id BIGINT NOT NULL REFERENCES suppliers(id),
    supply_date TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE INDEX idx_supplies_date
ON supplies(supply_date);

CREATE TABLE supply_lines(
    id BIGSERIAL PRIMARY KEY,
    supply_id BIGINT NOT NULL REFERENCES supplies(id) ON DELETE CASCADE,
    product_id BIGINT NOT NULL REFERENCES products(id),
    weight_kg NUMERIC(12, 3) NOT NULL,
    price_per_kg NUMERIC(12, 2) NOT NULL
);

CREATE INDEX idx_supply_lines_supply_id ON supply_lines(supply_id);
CREATE INDEX idx_supply_lines_product_id ON supply_lines(product_id);