package com.amalitech.services;

import com.amalitech.models.Project;
import com.amalitech.utils.exceptions.EmptyProjectException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProjectService {

    private final List<Project> projects;
    private int size = 0;

    /**
     * Initializes the ProjectService with an empty project list.
     */
    public ProjectService() {
        this.projects = new ArrayList<>();
    }

    public int getSize() {
        this.size = projects.size();
        return size;
    }

    /**
     * Adds a new project to the system.
     * @param project The project to be added.
     */
    public void addProject(Project project) {
        if (projects.add(project)) {
            System.out.println("Project added successfully!");
        } else {
            System.out.println("Failed to add project.");
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
            displayProjectHorizontal(projects.get(i));
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
            if (projects.get(i).getProjectDetails().equalsIgnoreCase(type)) {
                displayProjectHorizontal(projects.get(i));
                found = true;
            }
        }
        System.out.println("--------------------------------------------------------------------------------------");
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
            double budget = projects.get(i).getBudget();
            if (budget >= min && budget <= max) {
                displayProjectHorizontal(projects.get(i));
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
        return projects.removeIf(p -> p.getId() == projectId);
    }

    /**
     * Retrieves a project by its ID.
     * @param id The ID of the project to retrieve.
     * @return The project with the specified ID, or null if not found.
     */
    public Project getProjectById(int id) {
        return projects.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

     /**
      * Retrieves all projects.
      * @return An array of all projects.
      */
    public Project[] getProjects() {
        return projects.toArray(new Project[0]);
    }

    /**
     * Displays all tasks associated with the project.
     */
    public void displayAllTasks(Project project) {
        if (project.getTaskCount() == 0) {
            System.out.println("No tasks found for this project.");
            return;
        }

        System.out.println("\n---------------------------------------");
        System.out.println("Tasks for Project: " + project.getName());
        System.out.println("---------------------------------------");

        System.out.println("--------------------------------------------------");
        System.out.println("ID   | TASK NAME            | STATUS");
        System.out.println("--------------------------------------------------");
        for (int i = 0; i < project.getTaskCount(); i++) {
            System.out.printf("%-4d | %-20s | %-15s\n", project.getTasks().get(i).getId(), project.getTasks().get(i).getName(), project.getTasks().get(i).getStatus());
        }
        System.out.println("--------------------------------------------------");
        System.out.println("Total Tasks: " + project.getTaskCount());
        System.out.println("--------------------------------------------------");
    }

    /**
     * Displays the project details in a vertical format.
     */
    public void displayProject(Project project) {
        System.out.println("\n--------------------------------------------------");
        System.out.println("Project ID   : " + project.getId());
        System.out.println("Name         : " + project.getName());
        System.out.println("Team Size    : " + project.getTeamSize());
        System.out.println("Budget       : $" + project.getBudget());
        System.out.println("Description  : " + project.getDescription());
        System.out.println("Type         : " + project.getProjectDetails());
        System.out.println("--------------------------------------------------");
    }

    public void displayProjectHorizontal(Project project) {
        System.out.printf(
                "%-4d | %-20s | %-15s | %-10d | $%-10.2f | %s\n",
                project.getId(),
                project.getName(),
                project.getProjectDetails(),
                project.getTeamSize(),
                project.getBudget(),
                project.getDescription()
        );
    }
}
