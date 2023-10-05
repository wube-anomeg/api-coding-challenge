-- Create the Customer table
CREATE TABLE Customer (
                          id INT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL
);

-- Create the BankAccount table
CREATE TABLE BankAccount (
                             id INT PRIMARY KEY,
                             accountNumber VARCHAR(20) NOT NULL,
                             balance DECIMAL(10, 2) DEFAULT 0.00,
                             customer_id INT,
                             FOREIGN KEY (customer_id) REFERENCES Customer(id)
);

-- Create the FinancialTransaction table
CREATE TABLE FinancialTransaction (
                             id INT PRIMARY KEY,
                             description VARCHAR(255),
                             amount DECIMAL(10, 2),
                             timestamp TIMESTAMP,
                             sourceAccount_id INT,
                             targetAccount_id INT,
                             FOREIGN KEY (sourceAccount_id) REFERENCES BankAccount(id),
                             FOREIGN KEY (targetAccount_id) REFERENCES BankAccount(id)
);

-- Insert data into the Customer table
INSERT INTO Customer (name) VALUES
                                    ('Arisha Barron'),
                                    ('Branden Gibson'),
                                    ('Rhonda Church'),
                                    ('Georgina Hazel');
