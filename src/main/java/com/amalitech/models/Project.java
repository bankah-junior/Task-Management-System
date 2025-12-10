package com.amalitech.models;

public abstract class Project {

    private int id;
    private String name;
    private String description;
    private double budget;
    private int teamSize;

    private Task[] tasks = new Task[20];
    private int taskCount = 0;

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
        for (int i = 0; i < taskCount; i++) {
            if (tasks[i].getId() == task.getId()) {
                System.out.println("Task with ID " + task.getId() + " already exists!");
                return false;
            }
        }

        if (taskCount < tasks.length) {
            tasks[taskCount++] = task;
            System.out.printf("Task \"%s\" added successfully to Project %s\n", task.getName(), id);
            return true;
        } else {
            System.out.println("Task list is full!");
            return false;
        }
    }

     /**
     * Finds a task by its ID.
     * @param id The ID of the task to find.
     * @return The task with the specified ID, or null if not found.
     */
    public Task getTaskById(int id) {
        for (int i = 0; i < taskCount; i++) {
            if (tasks[i].getId() == id) {
                return tasks[i];
            }
        }
        return null;
    }

     /**
     * Returns the array of tasks associated with the project.
     * @return The array of tasks.
     */
    public Task[] getTasks() {
        return tasks;
    }

     /**
     * Returns the number of tasks associated with the project.
     * @return The number of tasks.
     */
    public int getTaskCount() {
        return taskCount;
    }

     /**
     * Decrements the task count by one.
     */
    public void decrementTaskCount() {
        if (taskCount > 0) taskCount--;
    }
}
