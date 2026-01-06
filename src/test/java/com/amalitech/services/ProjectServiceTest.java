package com.amalitech.services;

import com.amalitech.models.HardwareProject;
import com.amalitech.models.Project;
import com.amalitech.models.SoftwareProject;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ProjectService}.
 */
@DisplayName("ProjectService Tests")
class ProjectServiceTest {

    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        projectService = new ProjectService();
    }

    /**
     * Factory method to reduce duplication.
     */
    private Project createSoftwareProject() {
        return new SoftwareProject(
                1,
                "Alpha",
                "Software project for app",
                10_000,
                5
        );
    }

    @Nested
    @DisplayName("Add Project")
    class AddProjectTests {

        @Test
        @DisplayName("Should add project successfully")
        void TestShouldAddProject() {
            // Arrange
            Project project = createSoftwareProject();

            // Act
            projectService.addProject(project);

            // Assert
            assertNotNull(projectService.getProjectById(project.getId()));
            assertEquals(1, projectService.getSize());
        }
    }

    @Nested
    @DisplayName("Update Project")
    class UpdateProjectTests {

        @Test
        @DisplayName("Should update existing project")
        void testShouldUpdateProject() {
            // Arrange
            Project project = createSoftwareProject();
            projectService.addProject(project);

            // Act
            boolean updated = projectService.updateProject(
                    project.getId(),
                    "Beta",
                    "Updated description",
                    6,
                    15_000
            );

            // Assert
            assertTrue(updated);
            assertEquals("Beta",
                    projectService.getProjectById(project.getId()).getName());
        }
    }

    @Nested
    @DisplayName("Delete Project")
    class DeleteProjectTests {

        @Test
        @DisplayName("Should delete project by ID")
        void testShouldDeleteProject() {
            // Arrange
            Project project = createSoftwareProject();
            projectService.addProject(project);

            // Act
            boolean deleted = projectService.deleteProject(project.getId());

            // Assert
            assertTrue(deleted);
            assertNull(projectService.getProjectById(project.getId()));
            assertEquals(0, projectService.getSize());
        }
    }

    @Nested
    @DisplayName("Retrieve Projects")
    class RetrieveProjectTests {

        @Test
        @DisplayName("Should return project by ID")
        void testShouldGetProjectById() {
            // Arrange
            Project project = createSoftwareProject();
            projectService.addProject(project);

            // Act & Assert
            assertNotNull(projectService.getProjectById(project.getId()));
        }

        @Test
        @DisplayName("Should return correct project count")
        void testShouldReturnProjectSize() {
            // Arrange
            Project project = new HardwareProject(
                    2,
                    "Beta",
                    "Hardware project",
                    12_000,
                    4
            );

            // Act
            projectService.addProject(project);

            // Assert
            assertEquals(1, projectService.getSize());
        }
    }
}