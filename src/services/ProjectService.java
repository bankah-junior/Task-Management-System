package services;

import models.Project;

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

    public void addProject(Project project) {
        if (size < projects.length) {
            projects[size++] = project;
            System.out.println("Project added successfully!");
        } else {
            System.out.println("Project list is full!");
        }
    }

    // Display all projects
    public void displayAllProjects() {
        if (size == 0) {
            System.out.println("No projects available.");
            return;
        }
        System.out.println("---------------------------------------------------------------------");
        System.out.println("ID  | PROJECT NAME  | TYPE  |   TEAM SIZE   |   BUDGET  | DESCRIPTION");
        System.out.println("---------------------------------------------------------------------");
        for (int i = 0; i < size; i++) {
            projects[i].displayProjectHorizontal();
        }
        System.out.println("--------------------------------------------------");
        System.out.println("Total Projects: " + getSize());
        System.out.println("--------------------------------------------------");
    }

    // Filter projects by type
    public void filterByType(String type) {
        boolean found = false;
        for (int i = 0; i < size; i++) {
            if (projects[i].getProjectDetails().equalsIgnoreCase(type)) {
                projects[i].displayProject();
                found = true;
            }
        }
        if (!found) System.out.println("No projects found for type: " + type);
    }

    // Search by budget range
    public void searchByBudget(double min, double max) {
        boolean found = false;
        System.out.printf("Projects between $%.2f and $%.2f:\n", min, max);
        for (int i = 0; i < size; i++) {
            double budget = projects[i].getBudget();
            if (budget >= min && budget <= max) {
                projects[i].displayProject();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No projects found within the specified budget range.");
        }
    }

    // ------------------------
    // NEW: Update a project by ID
    // ------------------------
    public boolean updateProject(int projectId, String newName, String newDescription, int newTeamSize, double newBudget) {
        Project p = getProjectById(projectId);
        if (p == null) {
            System.out.println("Project ID not found.");
            return false;
        }

        // Use setters if added to Project, or reflect changes directly
        // If setters are not in Project, we can use a custom method or recreate the project object
        try {
            java.lang.reflect.Field nameField = Project.class.getDeclaredField("name");
            java.lang.reflect.Field descField = Project.class.getDeclaredField("description");
            java.lang.reflect.Field teamSizeField = Project.class.getDeclaredField("teamSize");
            java.lang.reflect.Field budgetField = Project.class.getDeclaredField("budget");

            nameField.setAccessible(true);
            descField.setAccessible(true);
            teamSizeField.setAccessible(true);
            budgetField.setAccessible(true);

            nameField.set(p, newName);
            descField.set(p, newDescription);
            teamSizeField.set(p, newTeamSize);
            budgetField.set(p, newBudget);

            System.out.println("Project updated successfully!");
            return true;
        } catch (Exception e) {
            System.out.println("Error updating project: " + e.getMessage());
            return false;
        }
    }

    // ------------------------
    // NEW: Delete a project by ID
    // ------------------------
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

    // Get project by ID
    public Project getProjectById(int id) {
        for (int i = 0; i < size; i++) {
            if (projects[i].getId() == id) return projects[i];
        }
        return null;
    }

    public Project[] getProjects() {
        return Arrays.copyOf(projects, size);
    }
}
