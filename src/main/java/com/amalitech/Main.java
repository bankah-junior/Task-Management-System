package com.amalitech;

import com.amalitech.utils.ConsoleMenu;
import com.amalitech.services.ProjectService;
import com.amalitech.services.TaskService;
import com.amalitech.services.UserService;
import com.amalitech.services.ReportService;
import com.amalitech.models.*;

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

        // ----------------- Start Menu -----------------
        ConsoleMenu menu = new ConsoleMenu(projectService, taskService, userService, reportService);
        menu.start(); // handles login and full menu

        // Close scanner on exit
        menu.close();
    }
}
