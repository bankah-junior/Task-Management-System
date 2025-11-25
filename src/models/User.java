package models;

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

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRole() { return role; }

    public List<Project> getAssignedProjects() { return assignedProjects; }
    public List<Task> getAssignedTasks() { return assignedTasks; }

    // Assign project
    public void assignProject(Project project) {
        if (!assignedProjects.contains(project)) {
            assignedProjects.add(project);
        }
    }

    // Assign task
    public void assignTask(Task task) {
        if (!assignedTasks.contains(task)) {
            assignedTasks.add(task);
        }
    }

    public void removeProject(Project project) {
        assignedProjects.remove(project);
    }

    public void removeTask(Task task) {
        assignedTasks.remove(task);
    }

    // Abstract method for role behavior
    public abstract void displayPermissions();

    @Override
    public String toString() {
        return "User ID: " + id +
                ", Name: " + name +
                ", Email: " + email +
                ", Role: " + role;
    }
}
