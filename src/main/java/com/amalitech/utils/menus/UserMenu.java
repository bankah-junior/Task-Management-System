package com.amalitech.utils.menus;

import com.amalitech.models.Project;
import com.amalitech.models.Task;
import com.amalitech.models.User;
import com.amalitech.services.ProjectService;
import com.amalitech.services.TaskService;
import com.amalitech.services.UserService;
import com.amalitech.utils.ValidationUtils;
import com.amalitech.utils.exceptions.InvalidInputException;

import java.util.Scanner;

public class UserMenu {
    private User loggedInUser;
    private final Scanner scanner;
    private final UserService userService;
    private final ProjectService projectService;
    private final TaskService taskService;
    GetMenuChoice gmc;

    public UserMenu(User loggedInUser, UserService userService, ProjectService projectService, TaskService taskService, Scanner scanner) {
        this.loggedInUser = loggedInUser;
        this.userService = userService;
        this.projectService = projectService;
        this.taskService = taskService;
        this.scanner = scanner;
        gmc = new GetMenuChoice(scanner);
    }

    public void userMenu() {
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
        int choice = gmc.getMenuChoice(6);
        switch (choice) {
            case 1 -> {
                System.out.print("Name: ");
                String name = "";
                boolean isValidName = false;
                while (!isValidName) {
                    name = scanner.nextLine();
                    if (!ValidationUtils.isValidName(name)) {
                        System.out.println("Enter a valid name!!");
                        System.out.print("Name: ");
                    } else {
                        isValidName = true;
                    }
                }
                System.out.print("Email: ");
                String email = "";
                boolean isValidEmail = false;
                while (!isValidEmail) {
                    try {
                        email = scanner.nextLine();
                        if (!ValidationUtils.isValidEmail(email)) {
                            System.out.println("Enter a valid email!!");
                            System.out.print("Email: ");
                        } else {
                            isValidEmail = true;
                        }
                    } catch (InvalidInputException e) {
                        System.out.println(e.getMessage());
                        System.out.print("Email: ");
                    }
                }
                System.out.print("Role (ADMIN/REGULAR_USER): ");
                String role = "";
                boolean isValidRole = false;
                while (!isValidRole) {
                    role = scanner.nextLine();
                    if (!role.equalsIgnoreCase("ADMIN") && !role.equalsIgnoreCase("REGULAR_USER")) {
                        System.out.println("Enter a valid role!!");
                        System.out.print("Role (ADMIN/REGULAR_USER): ");
                    } else {
                        isValidRole = true;
                    }
                }
                if (userService.createUser(name, email, role) == null) {
                    System.out.println("Failed to add user!!");
                }
            }
            case 2 -> userService.displayUsers();
            case 3 -> {
                System.out.print("User ID to delete: ");
                int id = gmc.getMenuChoice(userService.getUsers().size());
                if (userService.deleteUser(id)) {
                    System.out.println("User Deleted Successfully!!");
                } else {
                    System.out.println("Failed to Delete User!!");
                }
            }
            case 4 -> {
                System.out.print("Enter User ID: ");
                int userId = gmc.getMenuChoice(userService.getUsers().size());
                System.out.print("Enter Project ID: ");
                int projectId = gmc.getMenuChoice(projectService.getSize());
                User user = userService.login(userId);
                Project project = projectService.getProjectById(projectId);
                userService.assignUserToProject(user, project);
            }
            case 5 -> {
                System.out.print("Enter User ID: ");
                int userId = gmc.getMenuChoice(userService.getUsers().size());
                System.out.print("Enter Project ID: ");
                int projectId = gmc.getMenuChoice(projectService.getSize());
                System.out.print("Enter Task ID: ");
                int taskId = gmc.getMenuChoice(projectService.getProjectById(projectId).getTaskCount());
                User user = userService.login(userId);
                Project project = projectService.getProjectById(projectId);
                Task task = taskService.getTaskById(taskId, project.getTasks());
                userService.assignUserToTask(user, task);
            }
            case 6 -> { return; }
        }
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}
