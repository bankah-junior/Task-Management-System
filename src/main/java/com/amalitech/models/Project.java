package com.amalitech.models;

import java.util.ArrayList;
import java.util.List;

public abstract class Project {

    private int id;
    private String name;
    private String description;
    private double budget;
    private int teamSize;

    protected final List<Task> tasks = new ArrayList<>();

    public Project(int id, String name, String description, double budget, int teamSize) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.budget = budget;
        this.teamSize = teamSize;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getBudget() {
        return budget;
    }
    public int getTeamSize() { return teamSize; }
    public String getDescription() {
        return description;
    }

    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setTeamSize(int teamSize) { this.teamSize = teamSize; }
    public void setBudget(double budget) { this.budget = budget; }

    public abstract String getProjectDetails();

     /**
     * Adds a new task to the project.
     * @param task The task to be added.
     * @return true if the task was added successfully, false otherwise.
     */
    public boolean addTask(Task task) {
        // prevent duplicate IDs
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == task.getId()) {
                System.out.println("Task with ID " + task.getId() + " already exists!");
                return false;
            }
        }

        if (tasks.add(task)) {
            System.out.printf("Task \"%s\" added successfully to Project %s\n", task.getName(), id);
            return true;
        } else {
            System.out.println("Failed to add task \"" + task.getName() + "\" to Project " + id);
            return false;
        }
    }

     /**
     * Finds a task by its ID.
     * @param id The ID of the task to find.
     * @return The task with the specified ID, or null if not found.
     */
    public Task getTaskById(int id) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == id) {
                return tasks.get(i);
            }
        }
        return null;
    }

     /**
     * Returns the array of tasks associated with the project.
     * @return The array of tasks.
     */
    public List<Task> getTasks() {
        return tasks;
    }

     /**
     * Returns the number of tasks associated with the project.
     * @return The number of tasks.
     */
    public int getTaskCount() {
        return tasks.size();
    }

    /**
     * Converts the project to a JSON-formatted string.
     *
     * @return JSON representation of the project
     */
    public String toJson() {
        return String.format(
                "  {\n" +
                        "    \"id\": %d,\n" +
                        "    \"name\": \"%s\",\n" +
                        "    \"description\": \"%s\",\n" +
                        "    \"type\": \"%s\",\n" +
                        "    \"budget\": %.2f,\n" +
                        "    \"teamSize\": %d\n" +
                        "  }",
                id,
                name,
                description,
                getProjectDetails(),
                budget,
                teamSize
        );
    }

}
