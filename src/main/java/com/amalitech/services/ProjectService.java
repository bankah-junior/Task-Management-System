package com.amalitech.services;

import com.amalitech.models.HardwareProject;
import com.amalitech.models.Project;
import com.amalitech.models.SoftwareProject;
import com.amalitech.utils.FileUtils;
import com.amalitech.utils.exceptions.EmptyProjectException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ProjectService {

    private static final String PROJECT_FILE = "src/data/projects_data.json";
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

    public void displayAllProjects() throws EmptyProjectException {
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
    public List<Project> getProjects() {
        return projects;
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

    /**
     * Saves all projects to a JSON file.
     */
    public synchronized void saveProjects() {
        try {
            List<String> jsonLines = new ArrayList<>();
            jsonLines.add("[");

            for (int i = 0; i < projects.size(); i++) {
                jsonLines.add(projects.get(i).toJson() +
                        (i < projects.size() - 1 ? "," : ""));
            }

            jsonLines.add("]");

            FileUtils.writeAllLines(PROJECT_FILE, jsonLines);
//            System.out.println("Projects saved successfully.");
        } catch (IOException e) {
            System.out.println("Failed to save projects: " + e.getMessage());
        }
    }

    /**
     * Loads projects from a JSON file.
     */
    public void loadProjects() {
        try {
            List<String> lines = FileUtils.readAllLines(PROJECT_FILE);

            StringBuilder jsonObject = new StringBuilder();

            for (String line : lines) {
                line = line.trim();

                if (line.startsWith("{")) {
                    jsonObject.setLength(0);
                }

                if (!line.equals("[") && !line.equals("]")) {
                    jsonObject.append(line);
                }

                if (line.endsWith("},") || line.endsWith("}")) {
                    Map<String, String> values =
                            FileUtils.parseJsonObject(jsonObject.toString());

                    int id = Integer.parseInt(values.get("id"));
                    String name = values.get("name");
                    String description = values.get("description");
                    String type = values.get("type");
                    double budget = Double.parseDouble(values.get("budget"));
                    int teamSize = Integer.parseInt(values.get("teamSize"));

                    Project project = createProjectFromType(
                            id, name, description, budget, teamSize, type
                    );

                    projects.add(project);
                }
            }

            System.out.println("Projects loaded successfully.");

        } catch (Exception e) {
            System.out.println("Failed to load projects: " + e.getMessage());
        }
    }

    /**
     * Creates a project instance based on project type.
     *
     * @param id project ID
     * @param name project name
     * @param budget project budget
     * @param teamSize project team size
     * @param type project type
     * @return a Project instance
     */
    private Project createProjectFromType(int id,
                                          String name,
                                          String description,
                                          double budget,
                                          int teamSize,
                                          String type) {

        if ("Software".equalsIgnoreCase(type)) {
            return new SoftwareProject(id, name, description, budget, teamSize);
        }
        return new HardwareProject(id, name, description, budget, teamSize);
    }

}
