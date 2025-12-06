package com.amalitech.models;

import com.amalitech.interfaces.Completable;
import com.amalitech.utils.TaskStatus;

public class Task implements Completable {

    private int id;
    private String name;
    private TaskStatus status;
    private User assignedUser; // optional
    private int hours;

    public Task(int id, String name, TaskStatus status, int hours) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.hours = hours;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public void setStatus(TaskStatus status) { this.status = status; }
    public User getAssignedUser() { return assignedUser; }

    public void setName(String name) { this.name = name; }
    public void setAssignedUser(User user) { this.assignedUser = user; }

    /**
     * Marks the task as completed.
     */
    @Override
    public boolean isCompleted() { return status == TaskStatus.COMPLETED; }

     /**
     * Displays the task details in a vertical format.
     */
    public void displayTask() {
        System.out.printf("%-4d | %-20s | %-15s\n", id, name, status);
    }
}
