package com.amalitech.services;

import com.amalitech.models.Project;
import com.amalitech.models.SoftwareProject;
import com.amalitech.models.User;
import com.amalitech.utils.TaskStatus;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ReportService}.
 */
@DisplayName("ReportService Tests")
class ReportServiceTest {

    private ProjectService projectService;
    private TaskService taskService;
    private UserService userService;
    private ReportService reportService;

    private User admin;
    private User regularUser;
    private Project project;

    @BeforeEach
    void setUp() {
        projectService = new ProjectService(5);
        taskService = new TaskService();
        userService = new UserService();
        reportService = new ReportService();

        admin = userService.createUser("Alice", "alice@example.com", "ADMIN");
        regularUser = userService.createUser("Bob", "bob@example.com", "REGULAR_USER");

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
     * Helper method to add tasks to the test project.
     */
    private void addTask(String name, TaskStatus status, User user) {
        taskService.addTaskToProject(project, name, status, user, 5);
    }

    @Nested
    @DisplayName("Project Status Report")
    class ProjectStatusReportTests {

        @Test
        @DisplayName("Should generate correct project status report")
        void testShouldGenerateProjectStatus() {
            // Arrange
            addTask("Design UI", TaskStatus.TODO, regularUser);
            addTask("Develop Backend", TaskStatus.IN_PROGRESS, admin);
            addTask("Write Unit Tests", TaskStatus.COMPLETED, regularUser);

            // Act
            var report = reportService.generateProjectStatus(project);

            // Assert
            assertAll(
                    () -> assertEquals(3, report.getTotalTasks()),
                    () -> assertEquals(1, report.getCompletedTasks()),
                    () -> assertEquals(33.33,
                            report.getPercentageCompleted(),
                            0.01)
            );
        }
    }

    @Nested
    @DisplayName("Completion Rate")
    class CompletionRateTests {

        @Test
        @DisplayName("Should return 100% completion rate when all tasks are completed")
        void testShouldReturnFullCompletionRate() {
            // Arrange
            addTask("Design UI", TaskStatus.COMPLETED, regularUser);
            addTask("Develop Backend", TaskStatus.COMPLETED, admin);
            addTask("Write Unit Tests", TaskStatus.COMPLETED, regularUser);

            // Act
            double completionRate = reportService.completedRate(project);

            // Assert
            assertEquals(100.0, completionRate);
        }
    }
}
