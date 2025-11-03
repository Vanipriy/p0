CREATE DATABASE payroll_db;
USE payroll_db;

CREATE TABLE admin (
admin_id INT AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(50) UNIQUE NOT NULL,
password_hash VARCHAR(255) NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE employee (
emp_id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(100) NOT NULL,
department VARCHAR(50),
basic_salary DECIMAL(12,2) NOT NULL,
username VARCHAR(50) UNIQUE NOT NULL,
password_hash VARCHAR(255) NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE leaves (
leave_id INT AUTO_INCREMENT PRIMARY KEY,
emp_id INT NOT NULL,
from_date DATE NOT NULL,
to_date DATE NOT NULL,
reason VARCHAR(255),
status ENUM('PENDING','APPROVED','REJECTED') DEFAULT 'PENDING',
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (emp_id) REFERENCES employee(emp_id) ON DELETE CASCADE
);

CREATE TABLE allowances (
id INT AUTO_INCREMENT PRIMARY KEY,
emp_id INT NOT NULL,
hra DECIMAL(12,2) DEFAULT 0,
da DECIMAL(12,2) DEFAULT 0,
travel DECIMAL(12,2) DEFAULT 0,
medical DECIMAL(12,2) DEFAULT 0,
washing DECIMAL(12,2) DEFAULT 0,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (emp_id) REFERENCES employee(emp_id) ON DELETE CASCADE
);

CREATE TABLE salary (
salary_id INT AUTO_INCREMENT PRIMARY KEY,
emp_id INT NOT NULL,
month_year VARCHAR(10) NOT NULL,
gross_salary DECIMAL(12,2) NOT NULL,
deduction DECIMAL(12,2) DEFAULT 0,
net_salary DECIMAL(12,2) NOT NULL,
generated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (emp_id) REFERENCES employee(emp_id) ON DELETE CASCADE
);
use payroll_db;
-- USERS table
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'EMPLOYEE') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- EMPLOYEE table
CREATE TABLE employee (
    emp_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(20),
    department VARCHAR(100),
    base_salary DECIMAL(12,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- ALLOWANCES table
CREATE TABLE IF NOT EXISTS allowances (
    id INT AUTO_INCREMENT PRIMARY KEY,
    emp_id INT NOT NULL,
    hra DECIMAL(12,2) DEFAULT 0,
    da DECIMAL(12,2) DEFAULT 0,
    travel DECIMAL(12,2) DEFAULT 0,
    medical DECIMAL(12,2) DEFAULT 0,
    washing DECIMAL(12,2) DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (emp_id) REFERENCES employee(emp_id) ON DELETE CASCADE
);

-- SALARY table
CREATE TABLE IF NOT EXISTS salary (
    salary_id INT AUTO_INCREMENT PRIMARY KEY,
    emp_id INT NOT NULL,
    month_year VARCHAR(10) NOT NULL,
    gross_salary DECIMAL(12,2) NOT NULL,
    deduction DECIMAL(12,2) DEFAULT 0,
    net_salary DECIMAL(12,2) NOT NULL,
    generated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (emp_id) REFERENCES employee(emp_id) ON DELETE CASCADE
);

-- Insert Default Admin if not exists
INSERT INTO users (username, password, role)
SELECT 'admin', SHA2('admin123', 256), 'ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username='admin');
select * from users;
ALTER TABLE salary MODIFY month_year VARCHAR(20);
select * from salary;

DELETE FROM salary 
WHERE emp_id = 1 AND month_year = 'November-2025'
LIMIT 1;
ALTER TABLE salary 
ADD UNIQUE(emp_id, month_year);

INSERT INTO employee (name, department, basic_salary, username, password_hash)
VALUES ('Test Admin', 'IT', 50000, 'admin', '$2a$10$4UlH3SkZl8rUa2Xn0wF.vuCzLMYjoPmY9LLYj.kq.Ea/FfkvVj.6i');
DESC employee;
SELECT emp_id, name, username, password_hash FROM employee;
