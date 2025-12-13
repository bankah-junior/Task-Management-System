package com.amalitech.services;

import com.amalitech.models.Project;
import com.amalitech.models.Task;
import com.amalitech.models.User;
import com.amalitech.utils.FileUtils;
import com.amalitech.utils.TaskStatus;
import com.amalitech.utils.exceptions.TaskNotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.amalitech.utils.FileUtils.parseJsonObject;

public class TaskService {

    private static final String TASK_FILE = "src/data/tasks_data.json";
    private ProjectService projectService;

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
            Task t = project.getTasks().get(i);
            if (t.getName().equalsIgnoreCase(taskName)) {
                System.out.println("Task name already exists in this project!");
                return;
            }
        }

        Task task = new Task(project.getTasks().size() + 1, taskName, status, hours);

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
        List<Task> tasks = project.getTasks();
        int count = project.getTaskCount();
        boolean found = false;

        for (int i = 0; i < count; i++) {
            if (tasks.get(i).getId() == taskId) {
                tasks.remove(i);
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
    public Task getTaskById(int taskId, List<Task> tasks) {
        tasks.stream().filter(t -> t.getId() == taskId).findFirst()
                .ifPresent(t -> {
                    throw new RuntimeException("Task found: " + t.getName());
                });
        throw new TaskNotFoundException("Task with ID " + taskId + " not found.");
    }

    /**
     * Saves all tasks for all projects.
     *
     * @param projects list of projects
     */
    public void saveTasks(List<Project> projects) {
        try {
            List<String> jsonLines = new ArrayList<>();
            jsonLines.add("[");

            for (int i = 0; i < projects.size(); i++) {
                for (int j = 0; j < projects.get(i).getTasks().size(); j++) {
                    jsonLines.add(projects.get(i).getTasks().get(j).toJson(projects.get(i).getId()) + (j == projects.get(i).getTasks().size() - 1 && i == projects.size() - 1 ? "" : ","));
                }
            }

            if (jsonLines.size() > 1) {
                jsonLines.set(jsonLines.size() - 1,
                        jsonLines.get(jsonLines.size() - 1));
            }



            jsonLines.add("]");

            FileUtils.writeAllLines(TASK_FILE, jsonLines);
            System.out.println("Tasks saved successfully.");

        } catch (IOException e) {
            System.out.println("Failed to save tasks: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from persistence and attaches them to projects.
     *
     * @param projects list of projects
     */
    public void loadTasks(List<Project> projects) {

        try {
            List<String> lines = FileUtils.readAllLines(TASK_FILE);
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
                    var values = parseJsonObject(jsonObject.toString());

                    int id = Integer.parseInt(values.get("id"));
                    int projectId = Integer.parseInt(values.get("projectId"));
                    String name = values.get("name");
                    TaskStatus status = TaskStatus.valueOf(values.get("status"));
                    int hours = Integer.parseInt(values.get("estimatedHours"));
                    String userName = values.get("assignedUser");

                    Project project = projects.stream()
                            .filter(p -> p.getId() == projectId)
                            .findFirst()
                            .orElse(null);

                    if (project != null) {
                        Task task = new Task(id, name, status, hours);
                        project.addTask(task);
                    }
                }
            }

            System.out.println("Tasks loaded successfully.");

        } catch (Exception e) {
            System.out.println("Failed to load tasks: " + e.getMessage());
        }
    }
}
