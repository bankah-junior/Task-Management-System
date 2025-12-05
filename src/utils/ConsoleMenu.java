package utils;

import java.util.Scanner;
import services.ProjectService;
import services.TaskService;
import services.UserService;
import services.ReportService;
import models.*;
import utils.exceptions.TaskNotFoundException;

public class ConsoleMenu {

    private Scanner scanner;
    private ProjectService projectService;
    private TaskService taskService;
    private UserService userService;
    private ReportService reportService;
    private User loggedInUser;

    public ConsoleMenu(ProjectService ps, TaskService ts, UserService us, ReportService rs) {
        scanner = new Scanner(System.in);
        projectService = ps;
        taskService = ts;
        userService = us;
        reportService = rs;
    }

    public void start() {
        login();

        int choice;
        do {
            displayMainMenu();
            choice = getMenuChoice(6);

            switch (choice) {
                case 1 -> projectMenu();
                case 2 -> taskMenu();
                case 3 -> userMenu();
                case 4 -> reportMenu();
                case 5 -> login();
                case 6 -> System.out.println("Exiting... Goodbye!");
            }
        } while (choice != 6);
    }

    private void login() {
        System.out.println("\n==============================================");
        System.out.println("||  JAVA PROJECT MANAGEMENT SYSTEM (LOGIN)  ||");
        System.out.println("==============================================");
        userService.displayUsers();
        System.out.print("Enter your User ID to login: ");
        int userId;
        String input = scanner.nextLine();
        if (ValidationUtils.isInteger(input)) {
            userId = Integer.parseInt(input);
            loggedInUser = userService.login(userId);
            if (loggedInUser == null) {
                System.out.println("Invalid ID. Exiting.");
                System.exit(0);
            }
        } else {
            System.out.println("Enter a valid number!");
            System.exit(0);
        }
    }

    private void displayMainMenu() {
        System.out.println("\n======================================");
        System.out.println("||  JAVA PROJECT MANAGEMENT SYSTEM  ||");
        System.out.println("======================================");
        System.out.println("\nCurrent User: " + loggedInUser.getName() + " (" + loggedInUser.getRole() + ")");
        System.out.println("\nMain Menu");
        System.out.println("---------");
        System.out.println("1. Manage Project");
        System.out.println("2. Manage Tasks");
        System.out.println("3. Manage Users");
        System.out.println("4. View Status Reports");
        System.out.println("5. Switch User");
        System.out.println("6. Exit");
    }

    private int getMenuChoice(int maxRange) {
        int choice;
        while (true) {
            System.out.print("Enter choice: ");
            String input = scanner.nextLine();
            if (ValidationUtils.isInteger(input)) {
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > maxRange) System.out.println("Invalid option! \nChoice must be " + 1 + "-" + maxRange + ".");
                else break;
            } else {
                System.out.println("Enter a valid number!");
                continue;
            }
        }
        return choice;
    }

    private void projectMenu() {
        System.out.println("\n======================================");
        System.out.println("||          PROJECT CATALOG         ||");
        System.out.println("======================================");
        System.out.println("Project Menu --");
        System.out.println("1. Add Project (Admin Only)");
        System.out.println("2. Update Project (Admin Only)");
        System.out.println("3. Delete Project (Admin Only)");
        System.out.println("\nFilter options: ");
        System.out.println("4. View All Projects (" + projectService.getSize() + ")");
        System.out.println("5. Software Projects Only");
        System.out.println("6. Hardware Projects Only");
        System.out.println("7. Search by Budget range");
        System.out.println("8. Back");

        System.out.print("\nEnter filter choice: ");
        int choice = getMenuChoice(8);
        switch (choice) {
            case 1 -> {
                if (!loggedInUser.getRole().equals("ADMIN")) {
                    System.out.println("Access denied!");
                    return;
                }
                addProject();
            }
            case 2 -> {
                if (!loggedInUser.getRole().equals("ADMIN")) {
                    System.out.println("Access denied!");
                    return;
                }
                updateProject();
            }
            case 3 -> {
                if (!loggedInUser.getRole().equals("ADMIN")) {
                    System.out.println("Access denied!");
                    return;
                }
                deleteProject();
            }
            case 4 -> {
                projectService.displayAllProjects();
                System.out.print("\nEnter project id to view details (or 0 to return): ");
                int projectId = getMenuChoice(projectService.getSize());
                if (projectId > 0) {
                    Project project = projectService.getProjectById(projectId);
                    project.displayProject();
                    project.displayAllTasks();
                    System.out.println("Completion Rate: " + reportService.completedRate(project) + "%\n");
                } else if (projectId < 0) {
                    System.out.println("Invalid input!!");
                } else {
                    return;
                }
                taskMenu();
            }
            case 5 -> projectService.filterByType("Software Project");
            case 6 -> projectService.filterByType("Hardware Project");
            case 7 -> {
                System.out.print("Enter minimum budget: ");
                int min;
                String minInput = scanner.nextLine();
                if (!ValidationUtils.isInteger(minInput)) {
                    System.out.println("Enter a valid number!");
                    System.exit(0);
                }
                min = Integer.parseInt(minInput);
                System.out.print("Enter maximum budget: ");
                int max;
                String maxInput = scanner.nextLine();
                if (!ValidationUtils.isInteger(maxInput)) {
                    System.out.println("Enter a valid number!");
                    System.exit(0);
                }
                max = Integer.parseInt(maxInput);
                if (!ValidationUtils.isValidRange(min, max)) {
                    System.out.println("Max(" + max + ") should be greater than Min(" + min + ")");
                    System.exit(0);
                }
                projectService.searchByBudget(min,max);
            }
            case 8 -> { return; }
        }
    }

    private void addProject() {
        System.out.print("Enter Project Name: ");
        String name = scanner.nextLine();
        if (!ValidationUtils.isValidName(name)) {
            System.out.println("Enter a valid name!!");
            System.exit(0);
        }
        System.out.print("Description: ");
        String desc = scanner.nextLine();
        if (!ValidationUtils.isValidName(desc)) {
            System.out.println("Enter a valid description!!");
            System.exit(0);
        }
        System.out.print("Team Size: ");
        int teamSize;
        String teamSizeInput = scanner.nextLine();
        if (!ValidationUtils.isInteger(teamSizeInput)) {
            System.out.println("Enter a valid number!");
            System.exit(0);
        }
        teamSize = Integer.parseInt(teamSizeInput);
        if (teamSize < 0) {
            System.out.println("Team size cannot be negative!");
            System.exit(0);
        }
        System.out.print("Budget: ");
        double budget;
        String budgetInput = scanner.nextLine();
        if (!ValidationUtils.isDouble(budgetInput)) {
            System.out.println("Enter a valid number!");
            System.exit(0);
        }
        budget = Integer.parseInt(budgetInput);
        if (budget < 0) {
            System.out.println("Budget cannot be negative!");
            System.exit(0);
        }
        System.out.print("Type (Software/Hardware): ");
        String type = scanner.nextLine();
        Project project = null;
        if (type.equalsIgnoreCase("Software")) {
            project = new SoftwareProject(projectService.getSize() + 1, name, desc, budget, teamSize);
        } else if (type.equalsIgnoreCase("Hardware")) {
            project = new HardwareProject(projectService.getSize() + 1, name, desc, budget, teamSize);
        } else {
            System.out.println("Enter a valid type!!");
            System.exit(0);
        }

        projectService.addProject(project);
    }

    private void updateProject() {
        System.out.print("Enter Project ID to update: ");
        int id = getMenuChoice(projectService.getSize());
        System.out.print("New Name: "); String name = scanner.nextLine();
        System.out.print("New Description: "); String desc = scanner.nextLine();
        System.out.print("New Team Size: "); int teamSize = Integer.parseInt(scanner.nextLine());
        if (teamSize < 0) {
            System.out.println("Team size cannot be negative!");
            System.exit(0);
        }
        System.out.print("New Budget: "); double budget = Double.parseDouble(scanner.nextLine());
        if (budget < 0) {
            System.out.println("Budget cannot be negative!");
            System.exit(0);
        }

        if (projectService.updateProject(id, name, desc, teamSize, budget)) {
            System.out.println("Project Updated Successfully!");
        } else {
            System.out.println("Failed to Update Project!!");
        }
    }

    private void deleteProject() {
        System.out.print("Enter Project ID to delete: ");
        int id = getMenuChoice(projectService.getSize());
        if (projectService.deleteProject(id)) {
            System.out.println("Project Deleted Successfully!!");
        } else {
            System.out.println("Failed to Delete Project!!");
        }
    }

    private void taskMenu() {
        System.out.println("======================================");
        System.out.println("||             TASK MENU            ||");
        System.out.println("======================================");
        System.out.println("1. Add Task");
        System.out.println("2. Update Task");
        System.out.println("3. Remove Task");
        System.out.println("4. Back");

        System.out.print("Enter your choice: ");
        int choice = getMenuChoice(4);
        int projectId = 0;
        if (choice != 4) {
            System.out.print("Enter Project ID: ");
            projectId = getMenuChoice(projectService.getSize());

        }
        Project project = projectService.getProjectById(projectId);
        if (project == null) {
            System.out.println("Project not found!");
            return;
        }

        switch (choice) {
            case 1 -> {
                System.out.print("Task Name: ");
                String name = scanner.nextLine();
                System.out.print("Status (TODO, IN_PROGRESS, COMPLETED): ");
                try {
                    String statusInput = scanner.nextLine();
                    TaskStatus status = TaskStatus.valueOf(statusInput.toUpperCase());
                    System.out.print("Duration (Hours): ");
                    try {
                        String durationInput = scanner.nextLine();
                        if(!ValidationUtils.isInteger(durationInput)) {
                            System.out.println("\nInvalid duration! Please enter a valid number.");
                            return;
                        }
                        int duration = Integer.parseInt(durationInput);
                        if (duration <= 0) {
                            System.out.println("\nDuration must be greater than 0!");
                            return;
                        }
                        taskService.addTaskToProject(project, name, status, null, duration);
                    } catch (NumberFormatException e) {
                        System.out.println("\nInvalid duration! Please enter a valid number.");
                        return;
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("\nInvalid status! Use: TODO, IN_PROGRESS, or COMPLETED.");
                    return;
                }
            }
            case 2 -> {
                System.out.print("Task ID to update: ");
                int taskId = scanner.nextInt();
                scanner.nextLine();
                System.out.print("New Name (or press Enter to skip): ");
                String newName = scanner.nextLine();
                System.out.print("New Status (TODO, IN_PROGRESS, COMPLETED) or Enter to skip: ");
                String statusInput = scanner.nextLine();
                TaskStatus newStatus = null;
                if (!statusInput.isEmpty()) {
                    try {
                        newStatus = TaskStatus.valueOf(statusInput.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        System.out.println("\nInvalid status! Use: TODO, IN_PROGRESS, or COMPLETED.");
                        return;
                    }
                }
                try {
                    taskService.updateTask(project, taskId, newName.isEmpty() ? null : newName, newStatus);
                } catch (TaskNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }
            case 3 -> {
                System.out.print("Task ID to delete: ");
                String taskIdInput = scanner.nextLine();
                if (!ValidationUtils.isInteger(taskIdInput)) {
                    System.out.println("Enter a valid number!");
                    return;
                }
                int taskId = Integer.parseInt(taskIdInput);
                taskService.deleteTask(project, taskId);
            }
            case 4 -> { return; }
        }
    }

    private void userMenu() {
        if (!loggedInUser.getRole().equals("ADMIN")) {
            System.out.println("Access denied!");
            return;
        }

        System.out.println("\n======================================");
        System.out.println("||             USER MENU            ||");
        System.out.println("======================================");
        System.out.println("1. Add User");
        System.out.println("2. View Users");
        System.out.println("3. Delete User");
        System.out.println("4. Assign To Project");
        System.out.println("5. Assign To Task");
        System.out.println("6. Back");

        System.out.print("Enter your choice: ");
        int choice = getMenuChoice(6);
        switch (choice) {
            case 1 -> {
                System.out.print("Name: ");
                String name = scanner.nextLine();
                if (!ValidationUtils.isValidName(name)) {
                    System.out.println("Enter a valid name!!");
                    System.exit(0);
                }
                System.out.print("Email: ");
                String email = scanner.nextLine();
                if (!ValidationUtils.isValidEmail(email)) {
                    System.out.println("Enter a valid email!!");
                    System.exit(0);
                }
                System.out.print("Role (ADMIN/REGULAR_USER): ");
                String role = scanner.nextLine();
                if (userService.createUser(name, email, role) == null) {
                    System.out.println("Failed to add user!!");
                }
            }
            case 2 -> userService.displayUsers();
            case 3 -> {
                System.out.print("User ID to delete: ");
                int id = getMenuChoice(userService.getUsers().size());
                if (userService.deleteUser(id)) {
                    System.out.println("User Deleted Successfully!!");
                } else {
                    System.out.println("Failed to Delete User!!");
                }
            }
            case 4 -> {
                System.out.print("Enter User ID: ");
                int userId = getMenuChoice(userService.getUsers().size());
                System.out.print("Enter Project ID: ");
                int projectId = getMenuChoice(projectService.getSize());
                User user = userService.login(userId);
                Project project = projectService.getProjectById(projectId);
                userService.assignUserToProject(user, project);
            }
            case 5 -> {
                System.out.print("Enter User ID: ");
                int userId = getMenuChoice(userService.getUsers().size());
                System.out.print("Enter Project ID: ");
                int projectId = getMenuChoice(projectService.getSize());
                System.out.print("Enter Task ID: ");
                int taskId = getMenuChoice(projectService.getProjectById(projectId).getTaskCount());
                User user = userService.login(userId);
                Project project = projectService.getProjectById(projectId);
                Task task = taskService.getTaskById(taskId, project.getTasks());
                userService.assignUserToTask(user, task);
            }
            case 6 -> { return; }
        }
    }

    private void reportMenu() {
        System.out.println("\n======================================");
        System.out.println("||      PROJECT STATUS REPORT       ||");
        System.out.println("======================================");
        Project[] allProjects = projectService.getProjects();
        reportService.generateAllProjectReports(allProjects);
    }

    public void close() { scanner.close(); }
}
