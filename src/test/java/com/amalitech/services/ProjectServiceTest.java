package com.amalitech.services;

import com.amalitech.models.HardwareProject;
import com.amalitech.models.Project;
import com.amalitech.models.SoftwareProject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProjectServiceTest {
    @Test
    void testAddProject() {
        ProjectService projectService = new ProjectService();
        Project project = new SoftwareProject(1, "Alpha", "Software project for app", 10000, 5);
        projectService.addProject(project);
        Assertions.assertNotNull(projectService.getProjectById(project.getId()));
    }

    @Test
    void testUpdateProject() {
        ProjectService projectService = new ProjectService();
        Project project = new SoftwareProject(1, "Alpha", "Software project for app", 10000, 5);
        projectService.addProject(project);
        boolean updatedProject = projectService.updateProject(project.getId(), "Beta", "Updated description", 15000, 6);
        Assertions.assertTrue(updatedProject);
    }

    @Test
    void testDeleteProject() {
        ProjectService projectService = new ProjectService();
        Project project = new SoftwareProject(1, "Alpha", "Software project for app", 10000, 5);
        projectService.addProject(project);
        boolean deletedProject = projectService.deleteProject(project.getId());
        Assertions.assertTrue(deletedProject);
        Assertions.assertNull(projectService.getProjectById(project.getId()));
    }

    @Test
    void testGetProjectById() {
        ProjectService projectService = new ProjectService();
        Project project = new SoftwareProject(1, "Alpha", "Software project for app", 10000, 5);
        projectService.addProject(project);
        Assertions.assertNotNull(projectService.getProjectById(project.getId()));
    }

    @Test
    void testGetProjects() {
        ProjectService projectService = new ProjectService();
        Project project = new HardwareProject(1, "Alpha", "Hardware project for app", 10000, 5);
        projectService.addProject(project);
        Assertions.assertEquals(1, projectService.getSize());
    }

}