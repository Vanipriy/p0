# 💼 Employee Payroll Management System

A Java-based payroll management system designed to automate salary calculations, store employee details, and manage payroll operations efficiently. This system uses **MySQL Workbench** for database operations and **JDBC** for connectivity.

---

## ✅ Features

- Add, Update, Delete Employee Records
- Calculate Salary with:
  - Basic Pay
  - Allowances
  - Deductions
  - Net Salary
- Store employee payroll data securely in MySQL
- Command Line Interface (or UI if added)
- Error handling & validation

---

## 🛠️ Tech Stack

| Component | Technology |
|----------|-----------|
| Language | Java (Core) |
| Database | MySQL Workbench |
| Connectivity | JDBC (MySQL Connector/J) |
| IDE | IntelliJ IDEA / Eclipse / VS Code |
| Version Control | Git & GitHub |

---

## 📁 Project Folder Structure


## Database Setup

1. Open MySQL Workbench
2. File → Open SQL Script
3. Select `sql/payroll_db.sql`
4. Run to create tables and insert data

   
---

## 🔌 JDBC Connection Example

```java
String url = "jdbc:mysql://localhost:3306/payroll";
String user = "root";
String password = "your_password";

Connection conn = DriverManager.getConnection(url, user, password);
System.out.println("Database Connected Successfully!");

