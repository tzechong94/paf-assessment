CREATE DATABASE acme_bank;

USE acme_bank;

CREATE TABLE accounts (
    account_id VARCHAR(10) NOT NULL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    balance DECIMAL(15,2) NOT NULL
);


INSERT INTO accounts (account_id, name, balance) VALUES 
('V9L3Jd1BBI', 'fred', 100.00),
('fhRq46Y6vB', 'barney', 300.00),
('uFSFRqUpJy', 'wilma', 1000.00),
('ckTV56axff', 'betty', 1000.00),
('Qgcnwbshbh', 'pebbles', 50.00),
('if9l185l18', 'bambam', 50.00);

