# 📇 Contract Management System

A **console-based, role-driven contact management system** built with **Java** and **MySQL**.  
The application demonstrates clean object-oriented design, secure authentication, and database-backed data management with role-based access control.

---

## 📖 Overview

The **Contract Management System** is designed to manage users and contacts through a structured, permission-based workflow.  
Each authenticated user interacts with the system according to their assigned role, ensuring controlled access to operations.

The application:
- Runs entirely in the **terminal**
- Supports **Turkish characters**
- Uses **secure password hashing**
- Integrates with a **MySQL database**
- Follows **object-oriented programming principles**

---

## ✨ Key Features

- 🔐 Secure login & authentication
- 👥 Role-based authorization
- 📇 Contact management (CRUD operations)
- 🔎 Flexible search & sorting
- 🧮 Statistical insights for managers
- 🗄️ Persistent storage via MySQL
- 🎨 Console UI with ASCII animations
- 📚 Fully documented with JavaDoc

---

## 🧑‍💻 User Roles

The system supports four distinct roles, each with different permissions:

### 🧪 Tester
- Change password
- List all contacts
- Search contacts by one or more fields
- Sort results (ascending / descending)
- Logout

---

### 👨‍💻 Junior Developer
- All Tester permissions
- Update existing contact records

---

### 🧑‍💼 Senior Developer
- All Junior Developer permissions
- Add new contact(s)
- Delete contact(s)

---

### 👔 Manager
- Manage users (add, update, delete)
- View contact statistics
- Administrative oversight of the system

---

## 🗄️ Database Structure

The system is backed by a **MySQL database** consisting of two main tables:

### 👤 Users
Stores authentication and authorization data:
- Username
- Hashed password
- Name & surname
- Role
- Creation timestamp

### 📇 Contacts
Stores contact-related information:
- Names & nicknames
- Phone numbers
- Email & LinkedIn (optional)
- Birth date
- Audit timestamps

> All sensitive data is handled securely, and passwords are **never stored in plain text**.

---

## 🔍 Search & Sorting

The application provides:
- **Single-field search** (e.g., first name, last name, phone)
- **Multi-field search** with partial or exact matches
- **User-defined sorting** by any supported field (ASC / DESC)

This allows flexible querying without exposing database complexity to the user.

---

## 🛠️ Technologies Used

- Java
- Object-Oriented Programming (OOP)
- MySQL
- JDBC
- Java Console (CLI)
- Git & GitHub
- JavaDoc

---

## 🚀 Getting Started

### Clone the Repository
```bash
git clone https://github.com/nezihatklc/contract-management-system.git
cd contract-management-system
````

### Database Setup

1. Import the provided `.sql` file into MySQL
2. Update database credentials if necessary

### Compile & Run

```bash
javac *.java
java Main
```

---

## 📁 Project Structure

```
contract-management-system
├── src/
│   ├── model/
│   ├── service/
│   ├── repository/
│   ├── util/
│   └── Main.java
├── database/
│   └── schema.sql
├── README.md
└── .gitignore
```

---

## 📸 Screenshots 

* Login screen
  
  <img width="472" height="203" alt="image" src="https://github.com/user-attachments/assets/072a0b4d-2a95-47e2-8134-7e5fd6996354" />

* Contact list
  
  <img width="1811" height="908" alt="image" src="https://github.com/user-attachments/assets/9835e1e7-b1b7-4e20-92f6-de87912a086c" />


* Contact statistics
  
  <img width="772" height="895" alt="image" src="https://github.com/user-attachments/assets/0c564ea2-17f4-47f9-b4e2-2a4f3dfeacdc" />
  <img width="698" height="813" alt="image" src="https://github.com/user-attachments/assets/18ea0086-5c83-45d8-b279-070d2d6e8d78" />
  <img width="686" height="376" alt="image" src="https://github.com/user-attachments/assets/0cb4494a-cc2a-4965-a48d-c0b2f972bced" />


---

## 👩‍💻 Contributors

* Zeynep Sıla Şimşek
* Pelin Cömertler
* Simay Mutlu
* Nezihat Kılıç

---

