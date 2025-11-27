package services;

import models.Project;
import models.Task;
import models.User;
import utils.TaskStatus;

public class TaskService {

    private int nextTaskId = 1; // auto-incrementing task IDs

    // Add Task to Project
    public void addTaskToProject(Project project, String taskName, TaskStatus status, User assignedUser, int hours) {

        // Prevent duplicate names in the project
        for (int i = 0; i < project.getTaskCount(); i++) {
            Task t = project.getTasks()[i];
            if (t.getName().equalsIgnoreCase(taskName)) {
                System.out.println("Task name already exists in this project!");
                return;
            }
        }

        Task task = new Task(nextTaskId++, taskName, status, hours);

        // Optional: assign a user (if we extend Task to store User)
        if (assignedUser != null) {
            task.setAssignedUser(assignedUser);
        }

        project.addTask(task);
    }

    // Update Task (status or name)
    public void updateTask(Project project, int taskId, String newName, TaskStatus newStatus) {
        Task task = project.getTaskById(taskId);
        if (task == null) {
            System.out.println("Task not found!");
            return;
        }

        if (newName != null && !newName.trim().isEmpty()) {
            task.setName(newName);
        }

        if (newStatus != null) {
            task.setStatus(newStatus);
        }

        System.out.println("Task updated successfully!");
    }

    // Delete Task
    public void deleteTask(Project project, int taskId) {
        Task[] tasks = project.getTasks();
        int count = project.getTaskCount();
        boolean found = false;

        for (int i = 0; i < count; i++) {
            if (tasks[i].getId() == taskId) {
                // Shift left
                for (int j = i; j < count - 1; j++) {
                    tasks[j] = tasks[j + 1];
                }
                tasks[count - 1] = null;
                project.decrementTaskCount();
                found = true;
                System.out.println("Task deleted successfully!");
                break;
            }
        }

        if (!found) {
            System.out.println("Task not found in project.");
        }
    }

    public Task getTaskById(int taskId, Task[] tasks) {
        for (Task t : tasks) {
            if (t.getId() == taskId) {
                return t;
            }
        }
        System.out.println("Task ID not found.");
        return null;
    }
}
