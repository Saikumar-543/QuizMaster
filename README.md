# 🎯 Quiz Master – Java Quiz Application

A professional **console-based Quiz Application built using Core Java** that allows users to register, log in, take quizzes, view their scores, and track their quiz performance through a personalized dashboard.

The project is designed to demonstrate practical Java programming concepts such as **OOP, ArrayList, collections, methods, loops, conditional statements, and user input handling**.

---

## 📌 Project Overview

**Quiz Master** is a simple quiz management system where users can create an account, log in securely, choose a quiz category, answer multiple-choice questions, and receive their results immediately.

Each logged-in user has a personalized dashboard where they can:

* Start a quiz
* Select quiz categories
* View previous quiz results
* View profile information
* Track best and average scores
* Logout

The application currently stores data **in memory using ArrayList**, making it simple to understand and suitable for beginners learning Core Java.

---

## ✨ Features

### 👤 User Registration

* Create a new account
* Username validation
* Email validation
* Password confirmation
* Prevent duplicate usernames
* Prevent duplicate email addresses

### 🔐 User Login

* Username and password authentication
* Invalid login handling
* Personalized welcome message

### 📊 Professional Dashboard

After login, users get access to:

```text
1. Start Quiz
2. Quiz Categories
3. My Results
4. My Profile
5. Logout
6. Exit
```

### 📝 Quiz System

* Multiple-choice questions
* Four options per question
* Automatic answer checking
* Score calculation
* Percentage calculation
* Performance evaluation

### 📈 Result Tracking

Users can view:

* Previous quiz attempts
* Score
* Percentage
* Best score
* Average score
* Total quizzes attempted

### 👤 Personalized Profile

Displays information such as:

* Username
* Email
* Total quizzes
* Best score
* Average score

### 🚪 Logout

Users can safely logout and return to the login/register screen without terminating the application.

---

## 🛠️ Technologies Used

| Technology  | Purpose                               |
| ----------- | ------------------------------------- |
| Java        | Application development               |
| Core Java   | Business logic                        |
| OOP         | Application structure                 |
| ArrayList   | Data storage                          |
| Scanner     | User input                            |
| Collections | Managing users, questions and results |

No external frameworks or libraries are required.

---

## 📂 Project Structure

```text
QuizApplication/
│
├── User.java
├── Question.java
├── QuizResult.java
└── Main.java
```

### `User.java`

Stores user information:

```text
username
email
password
quiz statistics
```

### `Question.java`

Stores quiz question information:

```text
question
optionA
optionB
optionC
optionD
correctAnswer
category
```

### `QuizResult.java`

Stores quiz performance:

```text
category
totalQuestions
correctAnswers
score
percentage
```

### `Main.java`

Controls the complete application:

* Registration
* Login
* Dashboard
* Quiz
* Results
* Profile
* Logout

---

## 🔄 Application Flow

```text
                 START
                   |
                   ↓
             Welcome Screen
                   |
          ┌────────┼────────┐
          ↓        ↓        ↓
      Register    Login    Exit
                    |
                    ↓
               Dashboard
                    |
       ┌────────────┼─────────────┐
       ↓            ↓             ↓
   Start Quiz    My Results    My Profile
       |
       ↓
 Select Category
       |
       ↓
 Answer Questions
       |
       ↓
 Calculate Score
       |
       ↓
 Display Result
       |
       ↓
    Dashboard
```

---

## 🖥️ Sample Dashboard

```text
================================================
                  QUIZ MASTER
             Test Your Knowledge
================================================

Welcome, Sai!

---------------- DASHBOARD -------------------

1. Start Quiz
2. Quiz Categories
3. My Results
4. My Profile
5. Logout
6. Exit

-----------------------------------------------
Enter your choice:
```

---

## 📝 Sample Quiz

```text
----------------------------------------
Question 1 of 5
----------------------------------------

Which keyword is used to inherit a class?

A. implements
B. extends
C. super
D. this

Your Answer: B

✓ Correct!
```

---

## 📊 Sample Result

```text
========================================
             QUIZ COMPLETED
========================================

User       : Sai
Category   : Java

Total Questions : 5
Correct Answers : 4
Wrong Answers   : 1

Score      : 4 / 5
Percentage : 80%

Performance: VERY GOOD

========================================
```

---

## 🎯 Performance Levels

| Percentage | Performance       |
| ---------: | ----------------- |
|    90–100% | Excellent         |
|     75–89% | Very Good         |
|     60–74% | Good              |
|     40–59% | Average           |
|  Below 40% | Needs Improvement |

---

## 🚀 How to Run

### 1. Install Java

Install **JDK 8 or higher**.

Check your Java installation:

```bash
java -version
```

### 2. Clone the project

```bash
git clone <your-github-repository-url>
```

### 3. Open the project

Open the project in:

* IntelliJ IDEA
* Eclipse
* VS Code
* NetBeans

### 4. Compile

```bash
javac *.java
```

### 5. Run

```bash
java Main
```

---

## 🧪 Example User Flow

```text
1. Register
       ↓
2. Enter username, email and password
       ↓
3. Login
       ↓
4. Open Dashboard
       ↓
5. Start Java Quiz
       ↓
6. Answer questions
       ↓
7. View score
       ↓
8. View result history
       ↓
9. View profile
       ↓
10. Logout
```

---

## 🧠 Java Concepts Demonstrated

This project demonstrates practical usage of:

* Classes and Objects
* Constructors
* Encapsulation
* ArrayList
* Methods
* Loops
* `if-else`
* `switch`
* String handling
* Boolean values
* Object relationships
* Basic validation
* Searching collections
* Mathematical calculations

---

## ⚠️ Current Limitations

This is intentionally a simple Core Java application.

Currently:

* Data is stored in memory
* Data is lost when the application closes
* No real database
* No graphical interface
* No web interface
* Passwords are not securely encrypted

These limitations can be addressed in future versions.

---

## 🔮 Future Enhancements

### Version 2 – File Storage

Add file handling to permanently store:

* Users
* Questions
* Quiz results

### Version 3 – Database

Use:

```text
Java
 ↓
JDBC
 ↓
MySQL
```

### Version 4 – Spring Boot

Convert the application into a REST API:

```text
React Frontend
       ↓
Spring Boot
       ↓
MySQL
```

### Version 5 – Advanced Authentication

Add:

* Password hashing
* JWT authentication
* Role-based access

### Version 6 – Admin Dashboard

Admin can:

* Add questions
* Update questions
* Delete questions
* Create categories
* View users
* View overall quiz statistics

---

## 👨‍💻 Learning Objective

The main objective of this project is to understand how Core Java concepts can be combined to create a small real-world application.

Instead of writing isolated Java programs, this project demonstrates how **OOP, collections, user input, validation, business logic, and data management** work together in a complete application.

---

## 📄 Project Type

**Category:** Java Mini Project
**Application:** Quiz Management System
**Level:** Beginner / Intermediate
**Platform:** Console
**Language:** Java
**Architecture:** Object-Oriented Java Application

---

## 📜 License

This project is created for educational and learning purposes.
