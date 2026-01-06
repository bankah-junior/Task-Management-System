package com.amalitech.utils.menus;

import com.amalitech.models.Project;
import com.amalitech.services.ProjectService;
import com.amalitech.services.TaskService;
import com.amalitech.utils.TaskStatus;
import com.amalitech.utils.ValidationUtils;
import com.amalitech.utils.exceptions.TaskNotFoundException;

import java.util.Scanner;

public class TaskMenu {
    private final Scanner scanner;
    private final ProjectService projectService;
    private final TaskService taskService;
    GetMenuChoice gmc;

    public TaskMenu(ProjectService projectService, TaskService taskService, Scanner scanner) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.scanner = scanner;
        gmc = new GetMenuChoice(scanner);
    }

    public void taskMenu() {
        System.out.println("======================================");
        System.out.println("||             TASK MENU            ||");
        System.out.println("======================================");
        System.out.println("1. Add Task");
        System.out.println("2. Update Task");
        System.out.println("3. Remove Task");
        System.out.println("4. Back");

        System.out.print("Enter your choice: ");
        int choice = gmc.getMenuChoice(4);
        int projectId = 0;
        if (choice != 4) {
            System.out.print("Enter Project ID: ");
            projectId = gmc.getMenuChoice(projectService.getSize());

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

}
