INSERT INTO suppliers(name) VALUES
    ('Supplier A'),
    ('Supplier B'),
    ('Supplier C');

INSERT INTO products(code, name, category) VALUES
    ('APPLE_1', 'Apple type 1', 'APPLE'),
    ('APPLE_2', 'Apple type 2', 'APPLE'),
    ('PEAR_1', 'Pear type 1', 'PEAR'),
    ('PEAR_2', 'Pear type 2', 'PEAR');

INSERT INTO supplier_prices(supplier_id, product_id, valid_from, valid_to, price_per_kg)
SELECT s.id, p.id, DATE '2026-01-01', DATE '2026-12-31',
       CASE p.code
           WHEN 'APPLE_1' THEN 60.0
           WHEN 'APPLE_2' THEN 55.0
           WHEN 'PEAR_1' THEN 70.0
           WHEN 'PEAR_2' THEN 65.0
           END
FROM suppliers s
         JOIN products p ON true
WHERE s.name = 'Supplier A';

INSERT INTO supplier_prices(supplier_id, product_id, valid_from, valid_to, price_per_kg)
SELECT s.id, p.id, DATE '2026-01-01', DATE '2026-06-30',
       CASE p.code
           WHEN 'APPLE_1' THEN 58.0
           WHEN 'APPLE_2' THEN 53.0
           WHEN 'PEAR_1' THEN 72.0
           WHEN 'PEAR_2' THEN 66.0
           END
FROM suppliers s
         JOIN products p ON true
WHERE s.name = 'Supplier B';

INSERT INTO supplier_prices(supplier_id, product_id, valid_from, valid_to, price_per_kg)
SELECT s.id, p.id, DATE '2026-02-01', DATE '2026-12-31',
       CASE p.code
           WHEN 'APPLE_1' THEN 59.0
           WHEN 'APPLE_2' THEN 54.0
           WHEN 'PEAR_1' THEN 71.0
           WHEN 'PEAR_2' THEN 67.0
           END
FROM suppliers s
         JOIN products p ON true
WHERE s.name = 'Supplier C';
