# 🏨 Hotel Reservation System

Welcome to the **Hotel Reservation System**, a Java-based desktop application for efficiently managing hotel room reservations. Whether you're operating a small guest house or a mid-sized hotel, this system simplifies booking management, guest tracking, and record maintenance — all from a single console.

---

## 🌟 Features

* **Reserve a Room:** Add new reservations with guest name, room number, and contact information.
* **View Reservations:** Display all active reservations with details like reservation ID, guest name, room number, contact number, and reservation date.
* **Update Reservation:** Modify guest information, room numbers, or contact details for existing bookings.
* **Delete Reservation:** Remove outdated or cancelled reservations from the system.
* **Search Room by Guest:** Quickly find a guest’s assigned room number by their name and reservation ID.

---

## 🚀 Getting Started

### 🧩 Prerequisites

Make sure you have the following installed:

* **Java Development Kit (JDK 8 or above)**
* **MySQL Database Server (e.g., XAMPP or MySQL Workbench)**
* **MySQL Connector/J** (JDBC driver)

---

### ⚙️ Setup Instructions

#### 1. Clone the Repository

```bash
git clone https://github.com/your-username/Hotel-Reservation-System.git
```

#### 2. Create Database in MySQL

Login to your MySQL and run:

```sql
CREATE DATABASE hotel_db;
USE hotel_db;

CREATE TABLE reservation (
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    guest_name VARCHAR(50) NOT NULL,
    room_number INT NOT NULL,
    contact_number VARCHAR(20) NOT NULL,
    reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### 3. Configure Database Connection

Open your Java file (e.g., `Reservation.java`) and update these lines with your database credentials:

```java
private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
private static final String username = "root"; 
private static final String password = ""; 
```

#### 4. Compile and Run the Program

From IntelliJ Terminal or Command Prompt:

```bash
javac Reservation.java
java Reservation
```

---

## 💻 Usage

When you run the program, a console menu will appear:

```
HOTEL MANAGEMENT SYSTEM
1. Reserve a Room
2. View Reservations
3. Get Room Number
4. Update Reservation
5. Delete Reservation
6. Exit
```

Follow the prompts to perform the desired operation.

---

## 🧠 Example

```
Enter guest name: Anuj Mishra
Enter room number: 204
Enter contact number: 9876543210
Reservation successful!
```

---

## 🤝 Contributing

Contributions are welcome!
If you find a bug or want to suggest a new feature:

1. Fork this repository.
2. Create a new branch (`feature/new-feature-name`).
3. Commit your changes and open a pull request.

---

## 🙏 Acknowledgments

Special thanks to all contributors and Java developers who helped inspire this project.
Built with 💙 in Java + MySQL.

---

## 🌆 Happy Booking!

Simplify your hotel operations — manage reservations, update records, and keep everything organized effortlessly.
