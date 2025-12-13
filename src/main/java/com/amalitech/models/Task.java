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
    public User getAssignedUser() { return assignedUser; }
    public TaskStatus getStatus() {
        return status;
    }

    public void setName(String name) { this.name = name; }
    public void setAssignedUser(User user) { this.assignedUser = user; }
    public void setStatus(TaskStatus status) { this.status = status; }

    /**
     * Marks the task as completed.
     */
    @Override
    public boolean isCompleted() {
        return status == TaskStatus.COMPLETED;
    }

    /**
     * Converts the task to a JSON-formatted string.
     *
     * @param projectId the project this task belongs to
     * @return JSON representation of the task
     */
    public String toJson(int projectId) {
        return String.format(
                "  {\n" +
                        "    \"id\": %d,\n" +
                        "    \"projectId\": %d,\n" +
                        "    \"name\": \"%s\",\n" +
                        "    \"status\": \"%s\",\n" +
                        "    \"assignedUser\": \"%s\",\n" +
                        "    \"estimatedHours\": %d\n" +
                        "  }",
                id,
                projectId,
                name,
                status,
                assignedUser != null ? assignedUser.getName() : "Unassigned",
                hours
        );
    }


}
