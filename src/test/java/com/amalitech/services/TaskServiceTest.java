package com.amalitech.services;

import com.amalitech.models.Project;
import com.amalitech.models.SoftwareProject;
import com.amalitech.models.Task;
import com.amalitech.models.User;
import com.amalitech.utils.TaskStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {

    @Test
    void testAddTaskToProject() {
        TaskService taskService = new TaskService();
        ProjectService projectService = new ProjectService(10);
        UserService userService = new UserService();
        User regular1 = userService.createUser("Bob", "bob@example.com", "REGULAR_USER");
        Project project = new SoftwareProject(1, "Alpha", "Software project for app", 10000, 5);
        projectService.addProject(project);
        taskService.addTaskToProject(project, "Design UI", TaskStatus.TODO, regular1, 5);
        taskService.addTaskToProject(project, "Develop API", TaskStatus.TODO, regular1, 10);
        assertEquals(2, project.getTaskCount());
        assertEquals(1, taskService.getTaskById(1, project.getTasks()).getId());
        assertEquals(2, taskService.getTaskById(2, project.getTasks()).getId());
    }

    @Test
    void testGetTasksByProject() {
        TaskService taskService = new TaskService();
        ProjectService projectService = new ProjectService(10);
        UserService userService = new UserService();
        User regular1 = userService.createUser("Bob", "bob@example.com", "REGULAR_USER");
        Project project = new SoftwareProject(1, "Alpha", "Software project for app", 10000, 5);
        projectService.addProject(project);
        taskService.addTaskToProject(project, "Design UI", TaskStatus.TODO, regular1, 5);
        assertEquals(1, project.getTaskCount());
    }

    @Test
    void testGetTaskById() {
        TaskService taskService = new TaskService();
        ProjectService projectService = new ProjectService(10);
        UserService userService = new UserService();
        User regular1 = userService.createUser("Bob", "bob@example.com", "REGULAR_USER");
        Project project = new SoftwareProject(1, "Alpha", "Software project for app", 10000, 5);
        projectService.addProject(project);
        taskService.addTaskToProject(project, "Design UI", TaskStatus.TODO, regular1, 5);
        assertEquals(1, taskService.getTaskById(1, project.getTasks()).getId());
    }

    @Test
    void testUpdateTask() {
        TaskService taskService = new TaskService();
        ProjectService projectService = new ProjectService(10);
        UserService userService = new UserService();
        User regular1 = userService.createUser("Bob", "bob@example.com", "REGULAR_USER");
        Project project = new SoftwareProject(1, "Alpha", "Software project for app", 10000, 5);
        projectService.addProject(project);
        taskService.addTaskToProject(project, "Design UI", TaskStatus.TODO, regular1, 5);
        Task task = taskService.getTaskById(1, project.getTasks());
        taskService.updateTask(project, task.getId(), "Updated Design UI", TaskStatus.COMPLETED);
        Task updatedTask = taskService.getTaskById(1, project.getTasks());
        assertEquals("Updated Design UI", updatedTask.getName());
        assertTrue(updatedTask.isCompleted(), "Task status should be COMPLETED");
    }

    @Test
    void testDeleteTask() {
        TaskService taskService = new TaskService();
        ProjectService projectService = new ProjectService(10);
        UserService userService = new UserService();
        User regular1 = userService.createUser("Bob", "bob@example.com", "REGULAR_USER");
        Project project = new SoftwareProject(1, "Alpha", "Software project for app", 10000, 5);
        projectService.addProject(project);
        taskService.addTaskToProject(project, "Design UI", TaskStatus.TODO, regular1, 5);
        taskService.addTaskToProject(project, "Develop API", TaskStatus.TODO, regular1, 10);
        assertEquals(2, project.getTaskCount());
        taskService.deleteTask(project, 2);
        assertEquals(1, project.getTaskCount());
    }

}