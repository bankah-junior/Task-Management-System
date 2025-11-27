package services;

import models.Project;
import models.Task;
import models.StatusReport;
import models.User;

import java.util.HashMap;
import java.util.Map;

public class ReportService {

    public StatusReport generateProjectStatus(Project project) {

        if (project == null) {
            throw new IllegalArgumentException("Project cannot be null.");
        }

        Task[] tasks = project.getTasks();
        int total = project.getTaskCount();

        if (tasks == null || total == 0) {
            return new StatusReport(0, 0, 0, 0.0);
        }

        int completed = 0;
        int pending = 0;
        Map<String, Integer> userTaskSummary = new HashMap<>();

        for (int i = 0; i < total; i++) {
            Task t = tasks[i];
            if (t != null) {
                if (t.isCompleted()) {
                    completed++;
                } else {
                    pending++;
                }

                // Track per-user tasks (optional)
                if (t.getAssignedUser() != null) {
                    String username = t.getAssignedUser().getName();
                    userTaskSummary.put(username, userTaskSummary.getOrDefault(username, 0) + (t.isCompleted() ? 1 : 0));
                }
            }
        }

        double percent = (double) completed / total * 100;
        percent = Math.round(percent * 100) / 100.0;

        return new StatusReport(total, completed, pending, percent, userTaskSummary);
    }

    public void generateAllProjectReports(Project[] projects) {
        double totalCompletion = 0.0;
        double averageCompletion = 0.0;
        System.out.println("---------------------------------------------------------------------");
        System.out.println("PROJECT ID | PROJECT NAME    | TASKS      | COMPLETED  | PROGRESS    ");
        System.out.println("---------------------------------------------------------------------");
        for (Project project: projects) {
            StatusReport statusReport = generateProjectStatus(project);
            totalCompletion += statusReport.getPercentageCompleted();
            System.out.printf(
                    "%-10s | %-15s | %-10s | %-10s | %-8.2f%%\n",
                    project.getId(),
                    project.getName(),
                    statusReport.getTotalTasks(),
                    statusReport.getCompletedTasks(),
                    statusReport.getPercentageCompleted()
            );
        }
        averageCompletion = totalCompletion / projects.length;
        System.out.println("---------------------------------------------------------------------");
        System.out.println("AVERAGE COMPLETION: " + averageCompletion + "%");
        System.out.println("---------------------------------------------------------------------");
    }

    public double completedRate(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("Project cannot be null.");
        }

        Task[] tasks = project.getTasks();
        int total = project.getTaskCount();

        if (tasks == null || total == 0) {
            return 0.00;
        }

        int completed = 0;

        for (int i = 0; i < total; i++) {
            Task t = tasks[i];
            if (t != null) {
                if (t.isCompleted()) {
                    completed++;
                }
            }
        }

        double percent = (double) completed / total * 100;
        percent = Math.round(percent * 100) / 100.0;

        return percent;
    }
}
