-- TASK 1
create database HMBank;
use HMBank;

-- creating table Customer
CREATE TABLE Customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    DOB DATE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone_number VARCHAR(15) UNIQUE NOT NULL,
    address TEXT NOT NULL
);
desc Customers;

-- Create the Accounts table
CREATE TABLE Accounts (
    account_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    account_type ENUM('savings', 'current', 'zero_balance') NOT NULL,
    balance DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    FOREIGN KEY (customer_id) REFERENCES Customers(customer_id) ON DELETE CASCADE
);
desc Accounts;

-- Create the Transactions table
CREATE TABLE Transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT NOT NULL,
    transaction_type ENUM('deposit', 'withdrawal', 'transfer') NOT NULL,
    amount DECIMAL(15,2) NOT NULL CHECK (amount > 0),
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES Accounts(account_id) ON DELETE CASCADE
);

desc Transactions;
-- inserting values to table Customers
INSERT INTO Customers (first_name, last_name, DOB, email, phone_number, address) VALUES
('John', 'Doe', '1990-05-15', 'john.doe@example.com', '9876543210', '123 Main St, NY'),
('Jane', 'Smith', '1985-08-22', 'jane.smith@example.com', '8765432109', '456 Elm St, CA'),
('Alice', 'Johnson', '1992-11-10', 'alice.johnson@example.com', '7654321098', '789 Oak St, TX'),
('Bob', 'Brown', '1988-02-05', 'bob.brown@example.com', '6543210987', '321 Pine St, FL'),
('Charlie', 'Davis', '1995-07-30', 'charlie.davis@example.com', '5432109876', '987 Cedar St, WA'),
('Eve', 'Miller', '1993-12-25', 'eve.miller@example.com', '4321098765', '654 Birch St, OR'),
('Frank', 'Wilson', '1987-06-18', 'frank.wilson@example.com', '3210987654', '852 Maple St, NV'),
('Grace', 'Lee', '1991-09-14', 'grace.lee@example.com', '2109876543', '369 Willow St, CO'),
('Henry', 'Walker', '1983-03-27', 'henry.walker@example.com', '1098765432', '147 Spruce St, IL'),
('Ivy', 'Taylor', '1998-01-12', 'ivy.taylor@example.com', '1987654321', '753 Redwood St, AZ');

select * from Customers;

-- Insert sample data into Accounts table
INSERT INTO Accounts (customer_id, account_type, balance) VALUES
(1, 'savings', 5000.00),
(2, 'current', 10000.00),
(3, 'savings', 7500.50),
(4, 'zero_balance', 0.00),
(5, 'savings', 12000.75),
(6, 'current', 20000.00),
(7, 'savings', 3000.25),
(8, 'zero_balance', 0.00),
(9, 'current', 50000.00),
(10, 'savings', 1500.00);

select * from Accounts;

-- Insert sample data into Transactions table
INSERT INTO Transactions (account_id, transaction_type, amount) VALUES
(1, 'deposit', 2000.00),
(2, 'withdrawal', 1500.00),
(3, 'deposit', 3000.00),
(4, 'deposit', 500.00),
(5, 'withdrawal', 2000.50),
(6, 'deposit', 4000.00),
(7, 'withdrawal', 1000.00),
(8, 'deposit', 250.00),
(9, 'transfer', 5000.00),
(10, 'deposit', 800.00);

select * from Transactions;

-- Task 2
-- 1. Retrieve name, account type, and email of all customers
SELECT c.first_name, c.last_name, c.email, a.account_type FROM Customers c JOIN Accounts a ON c.customer_id = a.customer_id;

-- 2. List all transactions corresponding to each customer
SELECT c.first_name, c.last_name, a.account_id, t.transaction_id, t.transaction_type, t.amount, t.transaction_date FROM Customers c JOIN Accounts a ON c.customer_id = a.customer_id JOIN Transactions t ON a.account_id = t.account_id;

-- 3. Increase the balance of a specific account by a certain amount
UPDATE Accounts SET balance = balance + 500 WHERE account_id = 1;

-- 4. Combine first and last names of customers as full_name
SELECT CONCAT(first_name, ' ', last_name) AS full_name FROM Customers;

-- 5. Remove accounts with a balance of zero where the account type is savings
DELETE FROM Accounts WHERE balance = 0 AND account_type = 'savings';

-- 6. Find customers living in a specific city (e.g., 'NY')
SELECT * FROM Customers WHERE address LIKE '%NY%';

-- 7. Get the account balance for a specific account
SELECT balance FROM Accounts WHERE account_id = 1;

-- 8. List all current accounts with a balance greater than $1,000
SELECT * FROM Accounts WHERE account_type = 'current' AND balance > 1000;

-- 9. Retrieve all transactions for a specific account
SELECT * FROM Transactions WHERE account_id = 1;

-- 10. Calculate the interest accrued on savings accounts based on a given interest rate (e.g., 5%)
SELECT account_id, balance, (balance * 0.05) AS interest_accrued FROM Accounts WHERE account_type = 'savings';

-- 11. Identify accounts where the balance is less than a specified overdraft limit (e.g., -500)
SELECT * FROM Accounts WHERE balance < -500;

-- 12. Find customers not living in a specific city (e.g., 'NY')
SELECT * FROM Customers WHERE address NOT LIKE '%NY%';

-- TASK 3

# 1. Find the average account balance for all customers
SELECT AVG(balance) AS average_balance FROM Accounts;

# 2. Retrieve the top 10 highest account balances
SELECT * FROM Accounts ORDER BY balance DESC LIMIT 10;

# 3. Calculate Total Deposits for All Customers on a Specific Date (e.g., '2024-03-19')
SELECT SUM(amount) AS total_deposits 
FROM Transactions 
WHERE transaction_type = 'deposit' AND DATE(transaction_date) = '2024-03-19';

# 4. Find the Oldest and Newest Customers
SELECT * FROM Customers ORDER BY DOB ASC LIMIT 1;  -- Oldest
SELECT * FROM Customers ORDER BY DOB DESC LIMIT 1; -- Newest

# 5. Retrieve transaction details along with the account type
SELECT t.transaction_id, t.account_id, a.account_type, t.transaction_type, t.amount, t.transaction_date
FROM Transactions t
JOIN Accounts a ON t.account_id = a.account_id;

# 6. Get a list of customers along with their account details
SELECT c.customer_id, c.first_name, c.last_name, a.account_id, a.account_type, a.balance
FROM Customers c
JOIN Accounts a ON c.customer_id = a.customer_id;

# 7. Retrieve transaction details along with customer information for a specific account (e.g., account_id = 1)
SELECT t.transaction_id, t.transaction_type, t.amount, t.transaction_date, 
       c.customer_id, c.first_name, c.last_name, c.email
FROM Transactions t
JOIN Accounts a ON t.account_id = a.account_id
JOIN Customers c ON a.customer_id = c.customer_id
WHERE t.account_id = 1;

# 8. Identify customers who have more than one account
SELECT c.customer_id, c.first_name, c.last_name, COUNT(a.account_id) AS total_accounts
FROM Customers c
JOIN Accounts a ON c.customer_id = a.customer_id
GROUP BY c.customer_id
HAVING COUNT(a.account_id) > 1;

# 9. Calculate the difference in transaction amounts between deposits and withdrawals
SELECT account_id, 
       SUM(CASE WHEN transaction_type = 'deposit' THEN amount ELSE 0 END) - 
       SUM(CASE WHEN transaction_type = 'withdrawal' THEN amount ELSE 0 END) AS balance_difference
FROM Transactions
GROUP BY account_id;

# 10. Calculate the average daily balance for each account over a specified period (e.g., '2024-03-01' to '2024-03-19')
SELECT account_id, AVG(balance) AS avg_daily_balance
FROM Accounts
WHERE account_id IN (SELECT DISTINCT account_id FROM Transactions WHERE DATE(transaction_date) BETWEEN '2024-03-01' AND '2024-03-19')
GROUP BY account_id;

# 11. Calculate the total balance for each account type
SELECT account_type, SUM(balance) AS total_balance
FROM Accounts
GROUP BY account_type;

# 12. Identify accounts with the highest number of transactions ordered by descending order
SELECT account_id, COUNT(transaction_id) AS total_transactions
FROM Transactions
GROUP BY account_id
ORDER BY total_transactions DESC;

# 13. List customers with high aggregate account balances, along with their account types
SELECT c.customer_id, c.first_name, c.last_name, a.account_type, SUM(a.balance) AS total_balance
FROM Customers c
JOIN Accounts a ON c.customer_id = a.customer_id
GROUP BY c.customer_id, a.account_type
HAVING SUM(a.balance) > 10000  -- Adjust threshold as needed
ORDER BY total_balance DESC;

# 14. Identify and list duplicate transactions based on transaction amount, date, and account
SELECT account_id, amount, DATE(transaction_date) AS txn_date, COUNT(*) AS duplicate_count
FROM Transactions
GROUP BY account_id, amount, DATE(transaction_date)
HAVING COUNT(*) > 1;

 # TASK 4
 
 # 1. Retrieve the customer(s) with the highest account balance.
SELECT * FROM Customers 
WHERE customer_id IN (
    SELECT customer_id FROM Accounts 
    WHERE balance = (SELECT MAX(balance) FROM Accounts)
);

# 2. Calculate the average account balance for customers who have more than one account.
SELECT AVG(balance) AS avg_balance_multiple_accounts 
FROM Accounts 
WHERE customer_id IN (
    SELECT customer_id FROM Accounts 
    GROUP BY customer_id 
    HAVING COUNT(account_id) > 1
);

# 3. Retrieve accounts with transactions whose amounts exceed the average transaction amount.
SELECT * FROM Transactions 
WHERE amount > (
    SELECT AVG(amount) FROM Transactions
);

# 4. Identify customers who have no recorded transactions.
SELECT * FROM Customers 
WHERE customer_id NOT IN (
    SELECT DISTINCT customer_id FROM Accounts 
    WHERE account_id IN (SELECT DISTINCT account_id FROM Transactions)
);

# 5. Calculate the total balance of accounts with no recorded transactions.
SELECT SUM(balance) AS total_balance_no_transactions 
FROM Accounts 
WHERE account_id NOT IN (
    SELECT DISTINCT account_id FROM Transactions
);

# 6. Retrieve transactions for accounts with the lowest balance.
SELECT * FROM Transactions 
WHERE account_id IN (
    SELECT account_id FROM Accounts 
    WHERE balance = (SELECT MIN(balance) FROM Accounts)
);

# 7. Identify customers who have accounts of multiple types.
SELECT customer_id 
FROM Accounts 
GROUP BY customer_id 
HAVING COUNT(DISTINCT account_type) > 1;

# 8. Calculate the percentage of each account type out of the total number of accounts.
SELECT account_type, 
       (COUNT(*) * 100.0 / (SELECT COUNT(*) FROM Accounts)) AS percentage 
FROM Accounts 
GROUP BY account_type;

# 9. Retrieve all transactions for a customer with a given customer_id (e.g., 1).
SELECT * FROM Transactions 
WHERE account_id IN (
    SELECT account_id FROM Accounts WHERE customer_id = 1
);

# 10. Calculate the total balance for each account type, including a subquery within the SELECT clause.
SELECT account_type, 
       (SELECT SUM(balance) FROM Accounts AS A WHERE A.account_type = B.account_type) AS total_balance 
FROM Accounts AS B 
GROUP BY account_type;
