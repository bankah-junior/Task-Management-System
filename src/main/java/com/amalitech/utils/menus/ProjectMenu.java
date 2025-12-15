package com.amalitech.utils.menus;

import com.amalitech.models.HardwareProject;
import com.amalitech.models.Project;
import com.amalitech.models.SoftwareProject;
import com.amalitech.models.User;
import com.amalitech.services.ProjectService;
import com.amalitech.services.ReportService;
import com.amalitech.utils.ValidationUtils;
import com.amalitech.utils.exceptions.EmptyProjectException;

import java.util.Scanner;

public class ProjectMenu {
    private final Scanner scanner;
    private final ProjectService projectService;
    private final ReportService reportService;
    private final TaskMenu tm;
    private User loggedInUser;
    GetMenuChoice gmc;

    public ProjectMenu(ProjectService projectService, ReportService reportService, User loggedInUser, Scanner scanner, TaskMenu tm) {
        this.projectService = projectService;
        this.reportService = reportService;
        this.loggedInUser = loggedInUser;
        this.scanner = scanner;
        this.tm = tm;
        gmc = new GetMenuChoice(scanner);
    }

    public void displayProjectMenu() {
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
        int choice = gmc.getMenuChoice(8);
        switch (choice) {
            case 1 -> {
                if (!loggedInUser.getRole().equals("ADMIN")) {
                    System.out.println("Access denied!");
                    return;
                }
                addProjectMenu();
            }
            case 2 -> {
                if (!loggedInUser.getRole().equals("ADMIN")) {
                    System.out.println("Access denied!");
                    return;
                }
                updateProjectMenu();
            }
            case 3 -> {
                if (!loggedInUser.getRole().equals("ADMIN")) {
                    System.out.println("Access denied!");
                    return;
                }
                deleteProjectMenu();
            }
            case 4 -> {
                try {
                    projectService.displayAllProjects();
                } catch (EmptyProjectException e) {
                    System.out.println(e.getMessage());
                    return;
                }
                System.out.print("\nEnter project id to view details (or 0 to return): ");
                int projectId = gmc.getMenuChoice(projectService.getSize());
                if (projectId > 0) {
                    Project project = projectService.getProjectById(projectId);
                    projectService.displayProject(project);
                    projectService.displayAllTasks(project);
                    System.out.println("Completion Rate: " + reportService.completedRate(project) + "%\n");
                } else if (projectId < 0) {
                    System.out.println("Invalid input!!");
                } else {
                    return;
                }
                tm.taskMenu();
            }
            case 5 -> projectService.filterByType("Software Project");
            case 6 -> projectService.filterByType("Hardware Project");
            case 7 -> {
                System.out.print("Enter minimum budget: ");
                int min = 0;
                boolean isValidMin = false;
                while (!isValidMin) {
                    try {
                        String minInput = scanner.nextLine();
                        if (!ValidationUtils.isInteger(minInput)) {
                            System.out.println("Enter a valid number!");
                            System.out.print("Enter minimum budget: ");
                            continue;
                        }
                        min = Integer.parseInt(minInput);
                        isValidMin = true;
                    } catch (NumberFormatException e) {
                        System.out.println("Enter a valid number!");
                        System.out.print("Enter minimum budget: ");
                    }
                }
                System.out.print("Enter maximum budget: ");
                int max = 0;
                boolean isValidMax = false;
                while (!isValidMax) {
                    try {
                        String maxInput = scanner.nextLine();
                        if (!ValidationUtils.isInteger(maxInput)) {
                            System.out.println("Enter a valid number!");
                            System.out.print("Enter maximum budget: ");
                            continue;
                        }
                        max = Integer.parseInt(maxInput);
                        isValidMax = true;
                    } catch (NumberFormatException e) {
                        System.out.println("Enter a valid number!");
                        System.out.print("Enter maximum budget: ");
                    }
                }
                if (!ValidationUtils.isValidRange(min, max)) {
                    int temp = min;
                    min = max;
                    max = temp;
                }
                projectService.searchByBudget(min,max);
            }
            case 8 -> { return; }
        }
    }

    private void addProjectMenu() {
        System.out.print("Enter Project Name: ");
        String name = "";
        boolean isValidName = false;
        while (!isValidName) {
            name = scanner.nextLine();
            if (!ValidationUtils.isValidName(name)) {
                System.out.println("Enter a valid name!!");
                System.out.print("Enter Project Name: ");
            } else {
                isValidName = true;
            }
        }
        System.out.print("Description: ");
        String desc = "";
        boolean isValidDesc = false;
        while (!isValidDesc) {
            desc = scanner.nextLine();
            if (!ValidationUtils.isValidName(desc)) {
                System.out.println("Enter a valid description!!");
                System.out.print("Description: ");
            } else {
                isValidDesc = true;
            }
        }
        System.out.print("Team Size: ");
        int teamSize = 0;
        boolean isValidTeamSize = false;
        while (!isValidTeamSize) {
            try {
                String teamSizeInput = scanner.nextLine();
                if (!ValidationUtils.isInteger(teamSizeInput)) {
                    System.out.println("Enter a valid number!");
                    System.out.print("Team Size: ");
                    continue;
                }
                teamSize = Integer.parseInt(teamSizeInput);
                if (teamSize < 0) {
                    System.out.println("Team size cannot be negative!");
                    System.out.print("Team Size: ");
                    continue;
                }
                isValidTeamSize = true;
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number!");
                System.out.print("Team Size: ");
            }
        }
        System.out.print("Budget: ");
        double budget = 0;
        boolean isValidBudget = false;
        while (!isValidBudget) {
            try {
                String budgetInput = scanner.nextLine();
                if (!ValidationUtils.isDouble(budgetInput)) {
                    System.out.println("Enter a valid number!");
                    System.out.print("Budget: ");
                    continue;
                }
                budget = Double.parseDouble(budgetInput);
                if (budget < 0) {
                    System.out.println("Budget cannot be negative!");
                    System.out.print("Budget: ");
                    continue;
                }
                isValidBudget = true;
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number!");
                System.out.print("Budget: ");
            }
        }
        System.out.print("Type (Software/Hardware): ");
        String type = "";
        boolean isValidType = false;
        while (!isValidType) {
            type = scanner.nextLine();
            if (!type.equalsIgnoreCase("Software") && !type.equalsIgnoreCase("Hardware")) {
                System.out.println("Enter a valid type!!");
                System.out.print("Type (Software/Hardware): ");
            } else {
                isValidType = true;
            }
        }
        Project project = null;
        if (type.equalsIgnoreCase("Software")) {
            project = new SoftwareProject(projectService.getSize() + 1, name, desc, budget, teamSize);
        } else if (type.equalsIgnoreCase("Hardware")) {
            project = new HardwareProject(projectService.getSize() + 1, name, desc, budget, teamSize);
        }

        projectService.addProject(project);
    }

    private void updateProjectMenu() {
        System.out.print("Enter Project ID to update: ");
        int id = gmc.getMenuChoice(projectService.getSize());
        System.out.print("New Name: ");
        String name = "";
        boolean isValidName = false;
        while (!isValidName) {
            name = scanner.nextLine();
            if (!ValidationUtils.isValidName(name)) {
                System.out.println("Enter a valid name!!");
                System.out.print("New Name: ");
            } else {
                isValidName = true;
            }
        }
        System.out.print("New Description: ");
        String desc = "";
        boolean isValidDesc = false;
        while (!isValidDesc) {
            desc = scanner.nextLine();
            if (!ValidationUtils.isValidName(desc)) {
                System.out.println("Enter a valid description!!");
                System.out.print("New Description: ");
            } else {
                isValidDesc = true;
            }
        }
        System.out.print("New Team Size: ");
        int teamSize = 0;
        boolean isValidTeamSize = false;
        while (!isValidTeamSize) {
            try {
                String teamSizeInput = scanner.nextLine();
                if (!ValidationUtils.isInteger(teamSizeInput)) {
                    System.out.println("Enter a valid number!");
                    System.out.print("New Team Size: ");
                    continue;
                }
                teamSize = Integer.parseInt(teamSizeInput);
                if (teamSize < 0) {
                    System.out.println("Team size cannot be negative!");
                    System.out.print("New Team Size: ");
                    continue;
                }
                isValidTeamSize = true;
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number!");
                System.out.print("New Team Size: ");
            }
        }
        System.out.print("New Budget: ");
        double budget = 0;
        boolean isValidBudget = false;
        while (!isValidBudget) {
            try {
                String budgetInput = scanner.nextLine();
                if (!ValidationUtils.isDouble(budgetInput)) {
                    System.out.println("Enter a valid number!");
                    System.out.print("New Budget: ");
                    continue;
                }
                budget = Double.parseDouble(budgetInput);
                if (budget < 0) {
                    System.out.println("Budget cannot be negative!");
                    System.out.print("New Budget: ");
                    continue;
                }
                isValidBudget = true;
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number!");
                System.out.print("New Budget: ");
            }
        }

        if (projectService.updateProject(id, name, desc, teamSize, budget)) {
            System.out.println("Project Updated Successfully!");
        } else {
            System.out.println("Failed to Update Project!!");
        }
    }

    private void deleteProjectMenu() {
        System.out.print("Enter Project ID to delete: ");
        int id = gmc.getMenuChoice(projectService.getSize());
        if (projectService.deleteProject(id)) {
            System.out.println("Project Deleted Successfully!!");
        } else {
            System.out.println("Failed to Delete Project!!");
        }
    }

    public User setLoggedInUser(User user) {
        this.loggedInUser = user;
        return this.loggedInUser;
    }

}
