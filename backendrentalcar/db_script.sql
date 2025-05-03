-- Disable foreign key checks temporarily to avoid errors
SET FOREIGN_KEY_CHECKS = 0;

-- Drop tables if they exist
DROP TABLE IF EXISTS tb_car_accessory;
DROP TABLE IF EXISTS tb_car;
DROP TABLE IF EXISTS tb_accessory;
DROP TABLE IF EXISTS tb_brand;

-- Re-enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;

-- Create tables
CREATE TABLE tb_brand (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT
);

CREATE TABLE tb_accessory (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT
);

CREATE TABLE tb_car (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    brand_id BIGINT NOT NULL,
    FOREIGN KEY (brand_id) REFERENCES tb_brand(id) ON DELETE CASCADE
);

CREATE TABLE tb_car_accessory (
    car_id BIGINT NOT NULL,
    accessory_id BIGINT NOT NULL,
    PRIMARY KEY (car_id, accessory_id),
    FOREIGN KEY (car_id) REFERENCES tb_car(id) ON DELETE CASCADE,
    FOREIGN KEY (accessory_id) REFERENCES tb_accessory(id) ON DELETE CASCADE
);

-- Insert brands
INSERT INTO tb_brand (name, description) VALUES
('Volkswagen', 'Popular German brand known for reliability.'),
('Toyota', 'Japanese brand famous for efficiency and durability.'),
('Chevrolet', 'American brand known for its versatility.'),
('Hyundai', 'South Korean brand offering innovation and quality.'),
('Ford', 'American brand with strong presence in multiple markets.'),
('Fiat', 'Italian brand known for compact and practical cars.');

-- Insert accessories
INSERT INTO tb_accessory (name, description) VALUES
('GPS', 'Navigation system for guidance.'),
('Baby Seat', 'Safety seat for infants.'),
('Air Conditioning', 'Cooling system for comfort.'),
('Bluetooth', 'Wireless connectivity for devices.'),
('Sunroof', 'Transparent roof for natural light.'),
('Leather Seats', 'Premium seating material for luxury.');

-- Insert cars
INSERT INTO tb_car (name, model, brand_id) VALUES
('Gol', '1.6 Flex', (SELECT id FROM tb_brand WHERE name = 'Volkswagen')),
('Corolla', '2.0 Altis', (SELECT id FROM tb_brand WHERE name = 'Toyota')),
('Onix', '1.0 Turbo', (SELECT id FROM tb_brand WHERE name = 'Chevrolet')),
('HB20', '1.6 Vision', (SELECT id FROM tb_brand WHERE name = 'Hyundai')),
('EcoSport', '1.5 Titanium', (SELECT id FROM tb_brand WHERE name = 'Ford')),
('Punto', '1.4 Sporting', (SELECT id FROM tb_brand WHERE name = 'Fiat')),
('Focus', '2.0 SE', (SELECT id FROM tb_brand WHERE name = 'Ford')),
('Uno', '1.0 Way', (SELECT id FROM tb_brand WHERE name = 'Fiat')),
('T-Cross', '1.4 Highline', (SELECT id FROM tb_brand WHERE name = 'Volkswagen')),
('Camry', '3.5 XSE', (SELECT id FROM tb_brand WHERE name = 'Toyota'));

-- Associate cars with accessories
INSERT INTO tb_car_accessory (car_id, accessory_id) VALUES
((SELECT id FROM tb_car WHERE name = 'Gol'), (SELECT id FROM tb_accessory WHERE name = 'GPS')),
((SELECT id FROM tb_car WHERE name = 'Gol'), (SELECT id FROM tb_accessory WHERE name = 'Air Conditioning')),
((SELECT id FROM tb_car WHERE name = 'Corolla'), (SELECT id FROM tb_accessory WHERE name = 'GPS')),
((SELECT id FROM tb_car WHERE name = 'Corolla'), (SELECT id FROM tb_accessory WHERE name = 'Bluetooth')),
((SELECT id FROM tb_car WHERE name = 'Onix'), (SELECT id FROM tb_accessory WHERE name = 'Air Conditioning')),
((SELECT id FROM tb_car WHERE name = 'Onix'), (SELECT id FROM tb_accessory WHERE name = 'Baby Seat')),
((SELECT id FROM tb_car WHERE name = 'HB20'), (SELECT id FROM tb_accessory WHERE name = 'Bluetooth')),
((SELECT id FROM tb_car WHERE name = 'HB20'), (SELECT id FROM tb_accessory WHERE name = 'GPS')),
((SELECT id FROM tb_car WHERE name = 'EcoSport'), (SELECT id FROM tb_accessory WHERE name = 'Sunroof')),
((SELECT id FROM tb_car WHERE name = 'EcoSport'), (SELECT id FROM tb_accessory WHERE name = 'Leather Seats')),
((SELECT id FROM tb_car WHERE name = 'Punto'), (SELECT id FROM tb_accessory WHERE name = 'GPS')),
((SELECT id FROM tb_car WHERE name = 'Punto'), (SELECT id FROM tb_accessory WHERE name = 'Bluetooth')),
((SELECT id FROM tb_car WHERE name = 'Focus'), (SELECT id FROM tb_accessory WHERE name = 'Leather Seats')),
((SELECT id FROM tb_car WHERE name = 'Focus'), (SELECT id FROM tb_accessory WHERE name = 'Sunroof')),
((SELECT id FROM tb_car WHERE name = 'Uno'), (SELECT id FROM tb_accessory WHERE name = 'Air Conditioning')),
((SELECT id FROM tb_car WHERE name = 'Uno'), (SELECT id FROM tb_accessory WHERE name = 'Baby Seat')),
((SELECT id FROM tb_car WHERE name = 'T-Cross'), (SELECT id FROM tb_accessory WHERE name = 'Bluetooth')),
((SELECT id FROM tb_car WHERE name = 'T-Cross'), (SELECT id FROM tb_accessory WHERE name = 'Leather Seats')),
((SELECT id FROM tb_car WHERE name = 'Camry'), (SELECT id FROM tb_accessory WHERE name = 'GPS')),
((SELECT id FROM tb_car WHERE name = 'Camry'), (SELECT id FROM tb_accessory WHERE name = 'Sunroof'));

-- SQL query all data from the related tables, including cars, brands, and accessories
SELECT
    car.id AS car_id,
    car.name AS car_name,
    car.model AS car_model,
    brand.name AS brand_name,
    brand.description AS brand_description,
    accessory.name AS accessory_name,
    accessory.description AS accessory_description
FROM
    tb_car AS car
INNER JOIN
    tb_brand AS brand ON car.brand_id = brand.id
LEFT JOIN
    tb_car_accessory AS ca ON car.id = ca.car_id
LEFT JOIN
    tb_accessory AS accessory ON ca.accessory_id = accessory.id;