-- CASE STUDY

CREATE DATABASE ecommerce;
USE ecommerce;

-- 1. Create the Customers table
CREATE TABLE customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- 2. Create the Products table
CREATE TABLE products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    description TEXT,
    stock_quantity INT NOT NULL DEFAULT 0
);

-- 3. Create the Cart table
CREATE TABLE cart (
    cart_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
);

-- 4. Create the Orders table
CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_price DECIMAL(10,2) NOT NULL,
    shipping_address TEXT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE
);

-- 5. Create the Order_Items table
CREATE TABLE order_items (
    order_item_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
);

-- Adding data to the tables

-- Insert data into Customers table
INSERT INTO customers (name, email, password) VALUES ('Alice Johnson', 'alice@example.com', 'alice123');
INSERT INTO customers (name, email, password) VALUES ('Bob Smith', 'bob@example.com', 'bob123');

-- Insert data into Products table
INSERT INTO products (name, price, description, stock_quantity) VALUES ('Laptop', 1200.00, 'High-performance laptop', 10);
INSERT INTO products (name, price, description, stock_quantity) VALUES ('Smartphone', 800.00, 'Latest model smartphone', 15);

-- Insert data into Cart table
INSERT INTO cart (customer_id, product_id, quantity) VALUES (1, 1, 2);
INSERT INTO cart (customer_id, product_id, quantity) VALUES (2, 2, 1);

-- Insert data into Orders table
INSERT INTO orders (customer_id, total_price, shipping_address) VALUES (1, 2400.00, '123 Main St, City');
INSERT INTO orders (customer_id, total_price, shipping_address) VALUES (2, 800.00, '456 Oak St, Town');

-- Insert data into Order_Items table
INSERT INTO order_items (order_id, product_id, quantity) VALUES (1, 1, 2);
INSERT INTO order_items (order_id, product_id, quantity) VALUES (2, 2, 1);

-- Delete some data
DELETE FROM cart WHERE cart_id = 1;
DELETE FROM orders WHERE order_id = 1;

-- Add the deleted data back
INSERT INTO cart (customer_id, product_id, quantity) VALUES (1, 1, 2);
INSERT INTO orders (customer_id, total_price, shipping_address) VALUES (1, 2400.00, '123 Main St, City');

-- Execute Operations

-- Retrieve all customers
SELECT * FROM customers;

-- Retrieve all products
SELECT * FROM products;

-- Retrieve all orders with customer details
SELECT orders.order_id, customers.name, orders.total_price, orders.shipping_address 
FROM orders 
JOIN customers ON orders.customer_id = customers.customer_id;

-- Retrieve all cart items with product details
SELECT cart.cart_id, customers.name, products.name AS product_name, cart.quantity 
FROM cart 
JOIN customers ON cart.customer_id = customers.customer_id 
JOIN products ON cart.product_id = products.product_id;

-- Retrieve order details
SELECT order_items.order_item_id, orders.order_id, products.name AS product_name, order_items.quantity 
FROM order_items 
JOIN orders ON order_items.order_id = orders.order_id 
JOIN products ON order_items.product_id = products.product_id;