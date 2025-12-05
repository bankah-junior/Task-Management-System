package services;

import models.Project;
import utils.exceptions.EmptyProjectException;

import java.util.Arrays;

public class ProjectService {

    private Project[] projects;
    private int size = 0;

    public ProjectService(int capacity) {
        projects = new Project[capacity];
    }

    public int getSize() {
        return size;
    }

    /**
     * Adds a new project to the system.
     * @param project The project to be added.
     */
    public void addProject(Project project) {
        if (size < projects.length) {
            projects[size++] = project;
            System.out.println("Project added successfully!");
        } else {
            System.out.println("Project list is full!");
        }
    }

    public void displayAllProjects() {
        if (size == 0) {
            throw new EmptyProjectException("No projects available in the system.");
        }
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("ID   | PROJECT NAME         | TYPE             | TEAM SIZE  |   BUDGET   | DESCRIPTION");
        System.out.println("--------------------------------------------------------------------------------------");
        for (int i = 0; i < size; i++) {
            projects[i].displayProjectHorizontal();
        }
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("Total Projects: " + getSize());
        System.out.println("--------------------------------------------------------------------------------------");
    }

    /**
     * Filters projects by type.
     * @param type The type of project to filter by.
     */
    public void filterByType(String type) {
        boolean found = false;
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("ID   | PROJECT NAME         | TYPE             | TEAM SIZE  |   BUDGET   | DESCRIPTION");
        System.out.println("--------------------------------------------------------------------------------------");

        for (int i = 0; i < size; i++) {
            if (projects[i].getProjectDetails().equalsIgnoreCase(type)) {
                projects[i].displayProjectHorizontal();
                found = true;
            }
        }
        if (!found) System.out.println("No projects found for type: " + type);
    }

    /**
     * Searches for projects within a specified budget range.
     * @param min The minimum budget.
     * @param max The maximum budget.
     */
    public void searchByBudget(double min, double max) {
        boolean found = false;
        System.out.printf("Projects between $%.2f and $%.2f:\n", min, max);
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("ID   | PROJECT NAME         | TYPE             | TEAM SIZE  |   BUDGET   | DESCRIPTION");
        System.out.println("--------------------------------------------------------------------------------------");

        for (int i = 0; i < size; i++) {
            double budget = projects[i].getBudget();
            if (budget >= min && budget <= max) {
                projects[i].displayProjectHorizontal();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No projects found within the specified budget range.");
        }
    }

    /**
     * Updates project details.
     * @param projectId The ID of the project to update.
     * @param newName The new name of the project.
     * @param newDescription The new description of the project.
     * @param newTeamSize The new team size of the project.
     * @param newBudget The new budget of the project.
     * @return true if the project was updated successfully, false otherwise.
     */
    public boolean updateProject(int projectId, String newName, String newDescription, int newTeamSize, double newBudget) {
        Project p = getProjectById(projectId);
        if (p == null) {
            System.out.println("Project ID not found.");
            return false;
        }

        try {
            p.setName(newName);
            p.setDescription(newDescription);
            p.setTeamSize(newTeamSize);
            p.setBudget(newBudget);

            System.out.println("Project updated successfully!");
            return true;
        } catch (Exception e) {
            System.out.println("Error updating project: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deletes a project by its ID.
     * @param projectId The ID of the project to delete.
     * @return true if the project was deleted successfully, false otherwise.
     */
    public boolean deleteProject(int projectId) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (projects[i].getId() == projectId) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            System.out.println("Project ID not found.");
            return false;
        }

        // Shift elements to left
        for (int i = index; i < size - 1; i++) {
            projects[i] = projects[i + 1];
        }
        projects[size - 1] = null;
        size--;

        System.out.println("Project deleted successfully!");
        return true;
    }

    /**
     * Retrieves a project by its ID.
     * @param id The ID of the project to retrieve.
     * @return The project with the specified ID, or null if not found.
     */
    public Project getProjectById(int id) {
        for (int i = 0; i < size; i++) {
            if (projects[i].getId() == id) return projects[i];
        }
        return null;
    }

     /**
      * Retrieves all projects.
      * @return An array of all projects.
      */
    public Project[] getProjects() {
        return Arrays.copyOf(projects, size);
    }
}
