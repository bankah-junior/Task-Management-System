package services;

import models.Project;
import models.Task;
import models.User;
import utils.TaskStatus;
import utils.exceptions.TaskNotFoundException;

public class TaskService {

    private int nextTaskId = 1;

    /**
     * Adds a task to a project.
     * @param project The project to which the task will be added.
     * @param taskName The name of the task.
     * @param status The status of the task.
     * @param assignedUser The user assigned to the task (can be null if unassigned).
     * @param hours The estimated hours required for the task.
     */
    public void addTaskToProject(Project project, String taskName, TaskStatus status, User assignedUser, int hours) {

        for (int i = 0; i < project.getTaskCount(); i++) {
            Task t = project.getTasks()[i];
            if (t.getName().equalsIgnoreCase(taskName)) {
                System.out.println("Task name already exists in this project!");
                return;
            }
        }

        Task task = new Task(nextTaskId++, taskName, status, hours);

        if (assignedUser != null) {
            task.setAssignedUser(assignedUser);
        }

        if (project.addTask(task)){
            System.out.println("Task Added Successfully!");
        } else {
            System.out.println("Failed To Add Task!!");
        }
    }

    /**
     * Updates a task in a project.
     * @param project The project containing the task to update.
     * @param taskId The ID of the task to update.
     * @param newName The new name for the task (can be null to keep the current name).
     * @param newStatus The new status for the task (can be null to keep the current status).
     */
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

    /**
     * Deletes a task from a project.
     * @param project The project containing the task to delete.
     * @param taskId The ID of the task to delete.
     */
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

    /**
     * Retrieves a task by its ID from a list of tasks.
     * @param taskId The ID of the task to retrieve.
     * @param tasks The array of tasks to search.
     * @return The Task object with the specified ID.
     * @throws TaskNotFoundException if the task with the specified ID is not found.
     */
    public Task getTaskById(int taskId, Task[] tasks) {
        for (Task t : tasks) {
            if (t.getId() == taskId) {
                return t;
            }
        }
        throw new TaskNotFoundException("Task with ID " + taskId + " not found.");
    }
}
