package utils;

import java.util.Scanner;
import services.ProjectService;
import services.TaskService;
import services.UserService;
import services.ReportService;
import utils.ValidationUtils;
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
                case 3 -> reportMenu();
                case 4 -> login();
                case 5 -> System.out.println("Exiting... Goodbye!");
            }
        } while (choice != 5);
    }

    private void login() {
        System.out.println("\n==============================================");
        System.out.println("||  JAVA PROJECT MANAGEMENT SYSTEM (LOGIN)  ||");
        System.out.println("==============================================");
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
        System.out.println("\n======================================");
        System.out.println("||  JAVA PROJECT MANAGEMENT SYSTEM  ||");
        System.out.println("======================================");
        System.out.println("\nCurrent User: " + loggedInUser.getName() + " (" + loggedInUser.getRole() + ")");
        System.out.println("\nMain Menu");
        System.out.println("---------");
        System.out.println("1. Manage Project");
        System.out.println("2. Manage Tasks");
        System.out.println("3. View Status Reports");
        System.out.println("4. Switch User");
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
            if (choice < 1 || choice > 5) System.out.println("Invalid option! \nChoice must be 1-5.");
            else break;
        }
        return choice;
    }

    // ------------------- Project submenu -------------------
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
            case 4 -> {
                projectService.displayAllProjects();
                System.out.print("\nEnter project id to view details (or 0 to return): ");
                int projectId = scanner.nextInt();
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
                int min = scanner.nextInt();
                System.out.print("Enter maximum budget: ");
                int max = scanner.nextInt();
                projectService.searchByBudget(min,max);
            }
            case 8 -> { return; }
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
        System.out.println("======================================");
        System.out.println("||             TASK MENU            ||");
        System.out.println("======================================");
        System.out.println("1. Add Task");
        System.out.println("2. Update Task");
        System.out.println("3. Remove Task");
        System.out.println("4. Back");

        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        int projectId = 0;
        if (choice != 4) {
            System.out.print("Enter Project ID: ");
            projectId = scanner.nextInt();
        }
        Project project = projectService.getProjectById(projectId);
        if (project == null) {
            System.out.println("Project not found!");
            return;
        }

        switch (choice) {
            case 1 -> {
                scanner.nextLine();
                System.out.print("Task Name: ");
                String name = scanner.nextLine();
                System.out.print("Status (TODO, IN_PROGRESS, COMPLETED): ");
                TaskStatus status = TaskStatus.valueOf(scanner.nextLine().toUpperCase());
                System.out.print("Duration (Hours): ");
                int hours = scanner.nextInt();
                taskService.addTaskToProject(project, name, status, loggedInUser, hours);
            }
            case 2 -> {
                System.out.print("Task ID to update: ");
                int taskId = scanner.nextInt();
                scanner.nextLine();
                System.out.print("New Name (or press Enter to skip): ");
                String newName = scanner.nextLine();
                System.out.print("New Status (TODO, IN_PROGRESS, COMPLETED) or Enter to skip: ");
                String statusInput = scanner.nextLine();
                TaskStatus newStatus = statusInput.isEmpty() ? null : TaskStatus.valueOf(statusInput.toUpperCase());
                taskService.updateTask(project, taskId, newName.isEmpty() ? null : newName, newStatus);
            }
            case 3 -> {
                System.out.print("Task ID to delete: "); int taskId = Integer.parseInt(scanner.nextLine());
                taskService.deleteTask(project, taskId);
            }
            case 4 -> { return; }
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
        System.out.println("\n======================================");
        System.out.println("||      PROJECT STATUS REPORT       ||");
        System.out.println("======================================");
        Project[] allProjects = projectService.getProjects();
        reportService.generateAllProjectReports(allProjects);
    }

    // Close scanner
    public void close() { scanner.close(); }
}
