package models;

import interfaces.Completable;
import utils.TaskStatus;

public class Task implements Completable {

    private int id;
    private String name;
    private TaskStatus status;
    private User assignedUser; // optional

    public Task(int id, String name, TaskStatus status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public TaskStatus getStatus() { return status; }

    public void setStatus(TaskStatus status) { this.status = status; }

    public User getAssignedUser() { return assignedUser; }

    public void setAssignedUser(User user) { this.assignedUser = user; }

    @Override
    public void markComplete() {

    }

    @Override
    public boolean isCompleted() { return status == TaskStatus.COMPLETED; }

    public void displayTask() {
        System.out.println("   Task ID   : " + id);
        System.out.println("   Name      : " + name);
        System.out.println("   Status    : " + status);
        if (assignedUser != null) {
            System.out.println("   Assigned  : " + assignedUser.getName() + " (" + assignedUser.getRole() + ")");
        }
        System.out.println("-------------------------------------");
    }
}
