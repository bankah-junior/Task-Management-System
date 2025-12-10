# Task Management System (Java Console Application)

## Overview
This is a simple **Task Management System** built in Java using **OOP concepts**. It simulates project, task, and user management without a database. The system is console-based and supports:

- Project Catalog Management (Software/Hardware projects)
- Task Operations (add, update, delete tasks)
- User Management (Admin/Regular users, role-based access)
- Status Processing & Reporting (completion percentages, pending tasks)
- Menu Navigation & User Experience (interactive console menus)

---

## Features

### 1. Project Catalog Management
- Create software or hardware projects
- View all projects with details (ID, name, description, team size, budget)
- Filter projects by type

### 2. Task Operations
- Add tasks to specific projects
- Update task status or name
- Delete tasks
- Assign tasks to users
- View all tasks per project

### 3. User Management
- Create Admin or Regular users
- Assign users to projects or tasks
- Enforce role-based permissions
- Auto-generate unique user IDs

### 4. Status Reporting
- Display project completion percentage
- Display total, completed, pending tasks
- Optional per-user task completion summary

### 5. Menu Navigation
- Interactive main menu and submenus
- Input validation
- Role-based access enforcement
- Continuous loop until exit

---

## Project Structure

```
├── 📁 .mvn
├── 📁 src
│   ├── 📁 main
│   │   ├── 📁 java
│   │   │   └── 📁 com
│   │   │       └── 📁 amalitech
│   │   │           ├── 📁 interfaces
│   │   │           │   └── ☕ Completable.java
│   │   │           ├── 📁 models
│   │   │           │   ├── ☕ AdminUser.java
│   │   │           │   ├── ☕ HardwareProject.java
│   │   │           │   ├── ☕ Project.java
│   │   │           │   ├── ☕ RegularUser.java
│   │   │           │   ├── ☕ SoftwareProject.java
│   │   │           │   ├── ☕ StatusReport.java
│   │   │           │   ├── ☕ Task.java
│   │   │           │   └── ☕ User.java
│   │   │           ├── 📁 services
│   │   │           │   ├── ☕ ProjectService.java
│   │   │           │   ├── ☕ ReportService.java
│   │   │           │   ├── ☕ TaskService.java
│   │   │           │   └── ☕ UserService.java
│   │   │           ├── 📁 utils
│   │   │           │   ├── 📁 exceptions
│   │   │           │   │   ├── ☕ EmptyProjectException.java
│   │   │           │   │   ├── ☕ InvalidInputException.java
│   │   │           │   │   └── ☕ TaskNotFoundException.java
│   │   │           │   ├── ☕ ConsoleMenu.java
│   │   │           │   ├── ☕ TaskStatus.java
│   │   │           │   └── ☕ ValidationUtils.java
│   │   │           └── ☕ Main.java
│   │   └── 📁 resources
│   └── 📁 test
│       └── 📁 java
│           └── 📁 com
│               └── 📁 amalitech
│                   └── 📁 services
│                       ├── ☕ ProjectServiceTest.java
│                       ├── ☕ ReportServiceTest.java
│                       ├── ☕ TaskServiceTest.java
│                       └── ☕ UserServiceTest.java
├── ⚙️ .gitignore
├── 📝 APPROACH_USED.md
├── 🖼️ JUnit-Test-Results.png
├── 📝 README.md
├── 📝 THOUGHT_PROCESS.md
├── 📄 TaskManagementSystem.drawio
├── 🖼️ TaskManagementSystem.png
└── ⚙️ pom.xml
```

---

## Setup Instructions

1. **Clone or download** the project.
2. **Open in your IDE** (e.g., IntelliJ IDEA, Eclipse, VSCode).
3. **Compile all `.java` files**.
4. **Run `Main.java`** to start the console application.

---

## Usage Guide

1. **Login**
    - Select your User ID to log in.
    - Admins have full access; Regular users have limited permissions.

2. **Main Menu**
    - 1: Manage Projects
    - 2: Manage Tasks 
    - 3: Manage Users
    - 4: View Status Reports
    - 5: Switch User
    - 6: Exit

3. **Submenus**
    - Follow prompts to add, update, delete, view projects/tasks/users.
    - Use correct input types (numbers, strings, valid statuses).

4. **Exit**
    - Select `6` from the main menu to exit the application gracefully.

---

## Notes
- All IDs are auto-generated.
- Maximum projects: 20; maximum tasks per project: 20.
- All data is stored in memory (arrays, lists). Database integration can be added later.

---

## JUnit Test

### Steps
  1. Navigate into the `test folder`.
  2. Right-click on the `java folder`.
  3. Click on `Run All Tests`

### Results
![Results of JUnit test cases](docs/JUnit-Test-Results.png)

## Git Commit Logs

### Screenshot of some git commit logs

![Screenshot of Git Commit Logs](docs/Commit-Logs.png)