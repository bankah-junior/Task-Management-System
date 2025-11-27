
## Phase 1 (Requirement Gathering)

### 1. Read and Understand the project
	- The project is about designing a console based application/system using Java that allows users(with different roles: Admin and Regular) to add projects(two categories: Hardware and Software) and tasks. Based on their roles some actions should be restricted. A login is required to access the application/system. 
	- This is project is to use core Java with various OOP features.

### 2. Went through the features of the application/system, read and understood them very well. Because the features will help you know what to implement.

### 3. Understood how the system operates using the workflows

### 4. Went through the User Stories, all 5 epics and their sub stories, read and understood them very well. Because the stories will serve as a guide on what user might do.

### 5. Went through the given project structure to understand how to structure the entire work, what each file means and will contain.


NOTE:
* With the User Stories and the Features, most of the Workflows were covered because they gave me more clarity and understanding.



## Phase 2 (System Design)

### 1. Models:
	- User: id, name, email, role, assignedProjects, assignedTasks
	- AdminUser: inherits User
	- RegularUser: inherits User
	- Project: id, name, description, budget, teamSize
	- HardwareProject: inherits Project
	- SoftwareProject: inherits Project
	- Task: id, name, status, assignedUser (implements Completable interface)
	- StatusReport: totalTasks, completedTasks, pendingTasks, percentageCompleted

### 2. Interfaces:
	- Completable: markComplete(), isCompleted();

### 3. Utils:
	- TaskStatus(ENUM): TODO, IN_PROGRESS, COMPLETED
	- ValidationUtils: isValidNumber(), isValidInt(), isValidRange(), isValidName(), isValidEmail(), requireNonEmpty(), isInteger(), isDouble(), isValidTaskName()
	- ConsoleMenu: ProjectMenu, TaskMenu, UserMenu, ReportMenu

### 4. Services:
	- ProjectService: getSize(), addProject(), displayAllProjects(), filterByType(), searchByBudget(), updateProject(), deleteProject(), getProjectById()
	- TaskService: addTaskToProject(), updateTask(), deleteTask()
	- ReportService: generateProjectStatus()
	- UserService: createUser(), displayUser(), deleteUser(), assignUserToProject(), assignUserToTask(), login()



