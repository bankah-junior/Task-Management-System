package com.amalitech.utils.menus;

import java.util.List;
import java.util.Scanner;
import com.amalitech.services.ProjectService;
import com.amalitech.services.TaskService;
import com.amalitech.services.UserService;
import com.amalitech.services.ReportService;
import com.amalitech.models.*;
import com.amalitech.utils.ValidationUtils;
import com.amalitech.utils.exceptions.EmptyProjectException;
import com.amalitech.utils.exceptions.InvalidInputException;

public class ConsoleMenu {

    private Scanner scanner;
    private ProjectService projectService;
    private TaskService taskService;
    private UserService userService;
    private ReportService reportService;
    private User loggedInUser;
    TaskMenu tm;
    UserMenu um;
    ReportMenu rm;
    ProjectMenu pm;
    GetMenuChoice gmc;
    DisplayMainMenu dmm;

    public ConsoleMenu(ProjectService ps, TaskService ts, UserService us, ReportService rs) {
        scanner = new Scanner(System.in);
        projectService = ps;
        taskService = ts;
        userService = us;
        reportService = rs;
        tm = new TaskMenu(projectService, taskService, scanner);
        um = new UserMenu(loggedInUser, userService, projectService, taskService, scanner);
        rm = new ReportMenu(reportService, projectService);
        pm = new ProjectMenu(projectService, reportService, loggedInUser, scanner, tm);
        gmc = new GetMenuChoice(scanner);
        dmm = new DisplayMainMenu(loggedInUser);
    }

    public void start() {
        login();

        int choice;
        do {
            dmm.displayMainMenu();
            choice = gmc.getMenuChoice(6);

            switch (choice) {
                case 1 -> pm.displayProjectMenu();
                case 2 -> tm.taskMenu();
                case 3 -> um.userMenu();
                case 4 -> rm.reportMenu();
                case 5 -> login();
                case 6 -> {
                    System.out.print("Are you sure you want to save and exit? (yes/no): ");
                    String input = scanner.nextLine();
                    if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")) {
                        projectService.saveProjects();
                        taskService.saveTasks(projectService.getProjects());
                        userService.saveUsers();
                        System.out.println("Data saved. Exiting...");
                        System.exit(0);
                    } else {
                        choice = 0; // Reset choice to continue the loop
                    }
                }
            }
        } while (choice != 6);
    }

    private void login() {
        System.out.println("\n==============================================");
        System.out.println("||  JAVA PROJECT MANAGEMENT SYSTEM (LOGIN)  ||");
        System.out.println("==============================================");
        System.out.print("Enter your User ID to login: ");
        int userId;
        String input = scanner.nextLine();
        try {
            if (ValidationUtils.isInteger(input)) {
                userId = Integer.parseInt(input);
                loggedInUser = userService.login(userId);
                um.setLoggedInUser(loggedInUser);
                pm.setLoggedInUser(loggedInUser);
                dmm.setLoggedInUser(loggedInUser);
                if (loggedInUser == null) {
                    System.out.println("Invalid ID. Exiting.");
                    System.exit(0);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Try again.");
            login();
        }
    }

    public void close() { scanner.close(); }
}
