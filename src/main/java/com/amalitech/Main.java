package com.amalitech;

import com.amalitech.utils.ConsoleMenu;
import com.amalitech.services.ProjectService;
import com.amalitech.services.TaskService;
import com.amalitech.services.UserService;
import com.amalitech.services.ReportService;
import com.amalitech.models.*;
import com.amalitech.utils.TaskStatus;

public class Main {
    public static void main(String[] args) {
        // ----------------- Initialize Services -----------------
        ProjectService projectService = new ProjectService(20); // max 20 projects
        TaskService taskService = new TaskService();
        UserService userService = new UserService();
        ReportService reportService = new ReportService();

        // ----------------- Create some sample users -----------------
        User admin = userService.createUser("Alice", "alice@example.com", "ADMIN");
        User regular1 = userService.createUser("Bob", "bob@example.com", "REGULAR_USER");
        User regular2 = userService.createUser("Charlie", "charlie@example.com", "REGULAR_USER");

        // ----------------- Create sample projects -----------------
        Project p1 = new SoftwareProject(1, "Alpha", "Software project for app", 10000, 5);
        Project p2 = new HardwareProject(2, "Beta", "Hardware testing project", 15000, 3);
        Project p3 = new SoftwareProject(3, "Gamma", "New web platform", 20000, 6);
        Project p4 = new HardwareProject(4, "Delta", "IoT sensor hardware", 18000, 4);
        Project p5 = new SoftwareProject(5, "Epsilon", "Mobile game development", 12000, 5);
        Project p6 = new HardwareProject(6, "Zeta", "Robotics assembly", 25000, 8);

        projectService.addProject(p1);
        projectService.addProject(p2);
        projectService.addProject(p3);
        projectService.addProject(p4);
        projectService.addProject(p5);
        projectService.addProject(p6);

        // ----------------- Add sample tasks -----------------
        // Tasks for Project Alpha
        taskService.addTaskToProject(p1, "Design UI", TaskStatus.TODO, regular1, 5);
        taskService.addTaskToProject(p1, "Develop Backend", TaskStatus.IN_PROGRESS, regular2, 5);
        taskService.addTaskToProject(p1, "Write Unit Tests", TaskStatus.TODO, regular1, 5);

        // Tasks for Project Beta
        taskService.addTaskToProject(p2, "Setup Hardware Lab", TaskStatus.TODO, regular2, 5);
        taskService.addTaskToProject(p2, "Assemble Prototype", TaskStatus.IN_PROGRESS, regular1, 5);

        // Tasks for Project Gamma
        taskService.addTaskToProject(p3, "Create Wireframes", TaskStatus.TODO, regular1, 5);
        taskService.addTaskToProject(p3, "Implement Frontend", TaskStatus.TODO, regular2, 5);
        taskService.addTaskToProject(p3, "Setup Backend Server", TaskStatus.TODO, regular2, 5);

        // Tasks for Project Delta
        taskService.addTaskToProject(p4, "Procure Sensors", TaskStatus.TODO, regular1, 5);
        taskService.addTaskToProject(p4, "Assemble Circuit Boards", TaskStatus.IN_PROGRESS, regular2, 5);

        // Tasks for Project Epsilon
        taskService.addTaskToProject(p5, "Design Characters", TaskStatus.TODO, regular1, 5);
        taskService.addTaskToProject(p5, "Program Game Mechanics", TaskStatus.TODO, regular2, 5);

        // Tasks for Project Zeta
        taskService.addTaskToProject(p6, "Build Robot Frame", TaskStatus.TODO, regular1, 5);
        taskService.addTaskToProject(p6, "Install Motors", TaskStatus.TODO, regular2, 5);
        taskService.addTaskToProject(p6, "Test Movement", TaskStatus.TODO, regular2, 5);

        // ----------------- Start Menu -----------------
        ConsoleMenu menu = new ConsoleMenu(projectService, taskService, userService, reportService);
        menu.start(); // handles login and full menu

        // Close scanner on exit
        menu.close();
    }
}
