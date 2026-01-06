package com.amalitech.services;

import com.amalitech.models.Project;
import com.amalitech.models.SoftwareProject;
import com.amalitech.models.Task;
import com.amalitech.models.User;
import com.amalitech.utils.TaskStatus;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link TaskService}.
 */
@DisplayName("TaskService Tests")
class TaskServiceTest {

    private TaskService taskService;
    private ProjectService projectService;
    private UserService userService;

    private Project project;
    private User regularUser;

    @BeforeEach
    void setUp() {
        taskService = new TaskService();
        projectService = new ProjectService(10);
        userService = new UserService();

        regularUser = userService.createUser(
                "Bob",
                "bob@example.com",
                "REGULAR_USER"
        );

        project = new SoftwareProject(
                1,
                "Alpha",
                "Software project for app",
                10_000,
                5
        );

        projectService.addProject(project);
    }

    /**
     * Helper method to add a task to the test project.
     */
    private Task addTask(String name, TaskStatus status, int hours) {
        taskService.addTaskToProject(project, name, status, regularUser, hours);
        return taskService.getTaskById(project.getTaskCount(), project.getTasks());
    }

    @Nested
    @DisplayName("Add Task")
    class AddTaskTests {

        @Test
        @DisplayName("Should add multiple tasks to a project")
        void testShouldAddTasksToProject() {
            // Act
            addTask("Design UI", TaskStatus.TODO, 5);
            addTask("Develop API", TaskStatus.TODO, 10);

            // Assert
            assertAll(
                    () -> assertEquals(2, project.getTaskCount()),
                    () -> assertNotNull(taskService.getTaskById(1, project.getTasks())),
                    () -> assertNotNull(taskService.getTaskById(2, project.getTasks()))
            );
        }
    }

    @Nested
    @DisplayName("Retrieve Tasks")
    class RetrieveTaskTests {

        @Test
        @DisplayName("Should retrieve tasks by project")
        void testShouldGetTasksByProject() {
            // Arrange
            addTask("Design UI", TaskStatus.TODO, 5);

            // Assert
            assertEquals(1, project.getTaskCount());
        }

        @Test
        @DisplayName("Should retrieve task by ID")
        void testShouldGetTaskById() {
            // Arrange
            addTask("Design UI", TaskStatus.TODO, 5);

            // Act
            Task task = taskService.getTaskById(1, project.getTasks());

            // Assert
            assertNotNull(task);
            assertEquals(1, task.getId());
        }
    }

    @Nested
    @DisplayName("Update Task")
    class UpdateTaskTests {

        @Test
        @DisplayName("Should update task name and status")
        void testShouldUpdateTask() {
            // Arrange
            Task task = addTask("Design UI", TaskStatus.TODO, 5);

            // Act
            taskService.updateTask(
                    project,
                    task.getId(),
                    "Updated Design UI",
                    TaskStatus.COMPLETED
            );

            Task updatedTask =
                    taskService.getTaskById(task.getId(), project.getTasks());

            // Assert
            assertAll(
                    () -> assertEquals("Updated Design UI", updatedTask.getName()),
                    () -> assertTrue(updatedTask.isCompleted())
            );
        }
    }

    @Nested
    @DisplayName("Delete Task")
    class DeleteTaskTests {

        @Test
        @DisplayName("Should delete task from project")
        void testShouldDeleteTask() {
            // Arrange
            addTask("Design UI", TaskStatus.TODO, 5);
            addTask("Develop API", TaskStatus.TODO, 10);

            // Act
            taskService.deleteTask(project, 2);

            // Assert
            assertEquals(1, project.getTaskCount());
        }
    }
}
