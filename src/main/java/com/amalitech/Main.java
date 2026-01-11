package com.amalitech;

import com.amalitech.concurrency.AutoSaveTask;
import com.amalitech.utils.menus.ConsoleMenu;
import com.amalitech.services.ProjectService;
import com.amalitech.services.TaskService;
import com.amalitech.services.UserService;
import com.amalitech.services.ReportService;

public class Main {
    public static void main(String[] args) {
        // ----------------- Initialize Services -----------------
        ProjectService projectService = new ProjectService();
        TaskService taskService = new TaskService();
        UserService userService = new UserService();
        ReportService reportService = new ReportService();

        // Load initial data
        projectService.loadProjects();
        taskService.loadTasks(projectService.getProjects());
        userService.loadUsers();

        // Register shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\nShutting down...");
            userService.saveUsers();
            projectService.saveProjects();
            taskService.saveTasks(projectService.getProjects());
            System.out.println("Final save completed.");
        }));

        // ----------------- Start Auto-Save Thread -----------------
        AutoSaveTask autoSave = new AutoSaveTask(userService, projectService, taskService);
        Thread autoSaveThread = new Thread(autoSave, "AutoSave-Thread");
        autoSaveThread.setDaemon(true);
        autoSaveThread.start();


        // ----------------- Start Menu -----------------
        ConsoleMenu menu = new ConsoleMenu(projectService, taskService, userService, reportService);
        menu.start(); // handles login and full menu

        // Close scanner on exit
        menu.close();
    }
}
