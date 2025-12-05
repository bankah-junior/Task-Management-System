package com.amalitech.services;

import com.amalitech.models.Project;
import com.amalitech.models.SoftwareProject;
import com.amalitech.models.User;
import com.amalitech.utils.TaskStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReportServiceTest {

    @Test
    void testGenerateProjectStatus() {
        ProjectService projectService = new ProjectService(1);
        TaskService taskService = new TaskService();
        UserService userService = new UserService();
        User admin = userService.createUser("Alice", "alice@example.com", "ADMIN");
        User regular1 = userService.createUser("Bob", "bob@example.com", "REGULAR_USER");
        Project project = new SoftwareProject(1, "Alpha", "Software project for app", 10000, 5);
        projectService.addProject(project);
        taskService.addTaskToProject(project, "Design UI", TaskStatus.TODO, regular1, 5);
        taskService.addTaskToProject(project, "Develop Backend", TaskStatus.IN_PROGRESS, admin, 5);
        taskService.addTaskToProject(project, "Write Unit Tests", TaskStatus.COMPLETED, regular1, 5);
        ReportService reportService = new ReportService();
        var report = reportService.generateProjectStatus(project);
        assertEquals(3, report.getTotalTasks());
        assertEquals(1, report.getCompletedTasks());
        assertEquals(33.33, report.getPercentageCompleted());
    }

    @Test
    void testCompletedRate() {
        ProjectService projectService = new ProjectService(1);
        TaskService taskService = new TaskService();
        UserService userService = new UserService();
        User admin = userService.createUser("Alice", "alice@example.com", "ADMIN");
        User regular1 = userService.createUser("Bob", "bob@example.com", "REGULAR_USER");
        Project project = new SoftwareProject(1, "Alpha", "Software project for app", 10000, 5);
        projectService.addProject(project);
        taskService.addTaskToProject(project, "Design UI", TaskStatus.COMPLETED, regular1, 5);
        taskService.addTaskToProject(project, "Develop Backend", TaskStatus.COMPLETED, admin, 5);
        taskService.addTaskToProject(project, "Write Unit Tests", TaskStatus.COMPLETED, regular1, 5);
        ReportService reportService = new ReportService();
        var report = reportService.completedRate(project);
        assertEquals(100, report);
    }
}