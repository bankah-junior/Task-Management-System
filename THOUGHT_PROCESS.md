# Task Management System - Thought Process

## Objective
Design a console-based Task Management System in Java using **OOP concepts** (encapsulation, inheritance, polymorphism) and ensure it can be extended to use a database in the future.

---

## Features Breakdown

### Feature 1: Project Catalog Management
- **Problem:** Manage multiple projects with type-specific attributes.
- **Solution:**
    - Abstract `Project` class with common attributes.
    - Subclasses `SoftwareProject` and `HardwareProject`.
    - Store projects in an array, provide methods to view, filter, and display details.

### Feature 2: Task Operations
- **Problem:** Track progress for projects.
- **Solution:**
    - `Task` class with ID, name, status, optional assigned user.
    - `TaskService` to add/update/delete tasks.
    - Prevent duplicate tasks and invalid statuses.

### Feature 3: User Management
- **Problem:** Differentiate permissions for users.
- **Solution:**
    - Abstract `User` class with `AdminUser` and `RegularUser`.
    - Auto-generate IDs, assign users to projects/tasks.
    - Role-based access enforced in menu and services.

### Feature 4: Status Processing & Reporting
- **Problem:** Provide meaningful project progress information.
- **Solution:**
    - `ReportService` calculates total, completed, pending tasks.
    - Generates per-project status reports.
    - Optional per-user task completion summary for future expansion.

### Feature 5: Menu Navigation & UX
- **Problem:** Console navigation must be intuitive.
- **Solution:**
    - `ConsoleMenu` handles all menus, input validation, loops.
    - Role-based access checks included.
    - Graceful exit and clear prompts.

---

## Key Design Decisions
1. **OOP Principles**
    - Abstract classes and inheritance for Projects and Users.
    - Interfaces (`Completable`) for tasks to check completion.
    - Encapsulation with private fields and getters/setters.

2. **Services Layer**
    - Separate service classes for Projects, Tasks, Users, Reports.
    - Keeps logic modular and maintainable.

3. **Validation**
    - `ValidationUtils` handles numeric, string, and enum validation.
    - Ensures data integrity before adding/updating tasks/projects/users.

4. **Future Expansion**
    - Currently in-memory arrays/lists for storage.
    - Database migration can be added with minimal changes.
    - Reporting can be extended for per-user performance analytics.

---

## Challenges
- Designing a flexible yet simple menu system for multiple features.
- Ensuring role-based access was consistent throughout.
- Handling task assignment while maintaining clean object references.
- Calculating project status dynamically and rounding percentages.

---

## Conclusion
The system now fully supports:

- Project and task management
- User management with role-based permissions
- Status reporting with completion metrics
- Interactive console menu for easy navigation