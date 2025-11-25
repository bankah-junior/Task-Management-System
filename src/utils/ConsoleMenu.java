package utils;

import java.util.Scanner;
import services.ProjectService;
import services.TaskService;
import services.UserService;
import services.ReportService;
import models.*;

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

    // Main loop
    public void start() {
        login(); // simple user login

        int choice;
        do {
            displayMainMenu();
            choice = getMenuChoice();

            switch (choice) {
                case 1 -> projectMenu();
                case 2 -> taskMenu();
                case 3 -> userMenu();
                case 4 -> reportMenu();
                case 5 -> System.out.println("Exiting... Goodbye!");
            }
        } while (choice != 5);
    }

    private void login() {
        System.out.println("==== Task Management System Login ====");
        userService.displayUsers();
        System.out.print("Enter your User ID to login: ");
        int userId = Integer.parseInt(scanner.nextLine());
        loggedInUser = userService.login(userId);
        if (loggedInUser == null) {
            System.out.println("Invalid ID. Exiting.");
            System.exit(0);
        }
    }

    // Main menu
    private void displayMainMenu() {
        System.out.println("\n==== Main Menu ====");
        System.out.println("1. Project Operations");
        System.out.println("2. Task Operations");
        System.out.println("3. User Management");
        System.out.println("4. Reports");
        System.out.println("5. Exit");
    }

    private int getMenuChoice() {
        int choice;
        while (true) {
            System.out.print("Enter choice: ");
            String input = scanner.nextLine();
            if (!ValidationUtils.isInteger(input)) {
                System.out.println("Enter a valid number!");
                continue;
            }
            choice = Integer.parseInt(input);
            if (choice < 1 || choice > 5) System.out.println("Choice must be 1-5.");
            else break;
        }
        return choice;
    }

    // ------------------- Project submenu -------------------
    private void projectMenu() {
        System.out.println("\n-- Project Menu --");
        System.out.println("1. Add Project (Admin Only)");
        System.out.println("2. Update Project (Admin Only)");
        System.out.println("3. Delete Project (Admin Only)");
        System.out.println("4. View All Projects");
        System.out.println("5. Filter by Type");
        System.out.println("6. Back");

        int choice = Integer.parseInt(scanner.nextLine());
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
            case 4 -> projectService.displayAllProjects();
            case 5 -> filterProjectByType();
            case 6 -> { return; }
        }
    }

    private void addProject() {
        System.out.print("Enter Project Name: ");
        String name = scanner.nextLine();
        System.out.print("Description: ");
        String desc = scanner.nextLine();
        System.out.print("Team Size: ");
        int teamSize = Integer.parseInt(scanner.nextLine());
        System.out.print("Budget: ");
        double budget = Double.parseDouble(scanner.nextLine());
        System.out.print("Type (Software/Hardware): ");
        String type = scanner.nextLine();

        Project project;
        if (type.equalsIgnoreCase("Software")) {
            project = new SoftwareProject(projectService.getSize() + 1, name, desc, budget, teamSize);
        } else {
            project = new HardwareProject(projectService.getSize() + 1, name, desc, budget, teamSize);
        }
        projectService.addProject(project);
    }

    private void updateProject() {
        System.out.print("Enter Project ID to update: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("New Name: "); String name = scanner.nextLine();
        System.out.print("New Description: "); String desc = scanner.nextLine();
        System.out.print("New Team Size: "); int teamSize = Integer.parseInt(scanner.nextLine());
        System.out.print("New Budget: "); double budget = Double.parseDouble(scanner.nextLine());

        projectService.updateProject(id, name, desc, teamSize, budget);
    }

    private void deleteProject() {
        System.out.print("Enter Project ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        projectService.deleteProject(id);
    }

    private void filterProjectByType() {
        System.out.print("Enter project type to filter (Software/Hardware): ");
        String type = scanner.nextLine();
        projectService.filterByType(type);
    }

    // ------------------- Task submenu -------------------
    private void taskMenu() {
        System.out.println("\n-- Task Menu --");
        System.out.println("1. Add Task");
        System.out.println("2. Update Task");
        System.out.println("3. Delete Task");
        System.out.println("4. View Tasks of a Project");
        System.out.println("5. Back");

        int choice = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Project ID: ");
        int projectId = Integer.parseInt(scanner.nextLine());
        Project project = projectService.getProjectById(projectId);
        if (project == null) {
            System.out.println("Project not found!");
            return;
        }

        switch (choice) {
            case 1 -> {
                System.out.print("Task Name: "); String name = scanner.nextLine();
                System.out.print("Status (TODO, IN_PROGRESS, COMPLETED): ");
                TaskStatus status = TaskStatus.valueOf(scanner.nextLine().toUpperCase());
                taskService.addTaskToProject(project, name, status, loggedInUser);
            }
            case 2 -> {
                System.out.print("Task ID to update: "); int taskId = Integer.parseInt(scanner.nextLine());
                System.out.print("New Name (or press Enter to skip): "); String newName = scanner.nextLine();
                System.out.print("New Status (TODO, IN_PROGRESS, COMPLETED) or Enter to skip: ");
                String statusInput = scanner.nextLine();
                TaskStatus newStatus = statusInput.isEmpty() ? null : TaskStatus.valueOf(statusInput.toUpperCase());
                taskService.updateTask(project, taskId, newName.isEmpty() ? null : newName, newStatus);
            }
            case 3 -> {
                System.out.print("Task ID to delete: "); int taskId = Integer.parseInt(scanner.nextLine());
                taskService.deleteTask(project, taskId);
            }
            case 4 -> project.displayAllTasks();
            case 5 -> { return; }
        }
    }

    // ------------------- User submenu -------------------
    private void userMenu() {
        if (!loggedInUser.getRole().equals("ADMIN")) {
            System.out.println("Access denied!");
            return;
        }

        System.out.println("\n-- User Menu --");
        System.out.println("1. Add User");
        System.out.println("2. View Users");
        System.out.println("3. Delete User");
        System.out.println("4. Back");

        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1 -> {
                System.out.print("Name: "); String name = scanner.nextLine();
                System.out.print("Email: "); String email = scanner.nextLine();
                System.out.print("Role (ADMIN/REGULAR_USER): "); String role = scanner.nextLine();
                userService.createUser(name, email, role);
            }
            case 2 -> userService.displayUsers();
            case 3 -> {
                System.out.print("User ID to delete: "); int id = Integer.parseInt(scanner.nextLine());
                userService.deleteUser(id);
            }
            case 4 -> { return; }
        }
    }

    // ------------------- Report submenu -------------------
    private void reportMenu() {
        System.out.print("Enter Project ID for report: ");
        int projectId = Integer.parseInt(scanner.nextLine());
        Project project = projectService.getProjectById(projectId);
        if (project == null) {
            System.out.println("Project not found!");
            return;
        }
        StatusReport report = reportService.generateProjectStatus(project);
        System.out.println(report);
    }

    // Close scanner
    public void close() { scanner.close(); }
}
