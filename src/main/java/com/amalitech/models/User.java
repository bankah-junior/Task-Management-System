package com.amalitech.models;

import java.util.ArrayList;
import java.util.List;

public abstract class User {
    private static int idCounter = 1;

    protected int id;
    protected String name;
    protected String email;
    protected String role;

    protected List<Project> assignedProjects;
    protected List<Task> assignedTasks;

    public User(String name, String email, String role) {
        this.id = idCounter++;
        this.name = name;
        this.email = email;
        this.role = role;
        this.assignedProjects = new ArrayList<>();
        this.assignedTasks = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRole() { return role; }

    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Assigns a project to the user.
     * @param project The project to assign.
     */
    public void assignProject(Project project) {
        if (!assignedProjects.contains(project)) {
            assignedProjects.add(project);
        }
    }

    /**
     * Assigns a task to the user.
     * @param task The task to assign.
     */
    public void assignTask(Task task) {
        if (!assignedTasks.contains(task)) {
            assignedTasks.add(task);
        }
    }

     /**
     * Displays the permissions of the user.
     */
    public abstract void displayPermissions();

    @Override
    public String toString() {
        return String.format(
                "%-5d | %-15s | %-25s | %-12s",
                id,
                name,
                email,
                role
        );
    }

}
