package com.amalitech.services;

import com.amalitech.models.Project;
import com.amalitech.models.Task;
import com.amalitech.models.StatusReport;
import com.amalitech.utils.FunctionalUtils;
import com.amalitech.utils.TaskStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportService {

    /**
     * Generates a project status report.
     *
     * @param project the project to analyze
     * @return a StatusReport instance
     */
    public StatusReport generateProjectStatus(Project project) {

        long totalTasks = project.getTasks().size();
        long completedTasks = FunctionalUtils.filterTasks(project.getTasks(), t -> t.getStatus() == TaskStatus.COMPLETED).size();

        long pendingTasks = totalTasks - completedTasks;

        double percentageCompleted = totalTasks == 0
                ? 0.0
                : (completedTasks * 100.0) / totalTasks;

        Map<String, Integer> userSummary = project.getTasks().stream()
                .filter(task -> task.getStatus() == TaskStatus.COMPLETED)
                .collect(Collectors.groupingBy(
                        Task::getName,
                        Collectors.summingInt(t -> 1)
                ));

        return new StatusReport(
                (int) totalTasks,
                (int) completedTasks,
                (int) pendingTasks,
                percentageCompleted,
                userSummary
        );
    }

    /**
     * Generates status reports for all projects.
     * @param projects The array of projects for which to generate status reports.
     */
    public void generateAllProjectReports(List<Project> projects) {
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
        averageCompletion = totalCompletion / projects.size();
        System.out.println("---------------------------------------------------------------------");
        System.out.println("AVERAGE COMPLETION: " + averageCompletion + "%");
        System.out.println("---------------------------------------------------------------------");
    }

    /**
     * Calculates the completion rate of a project.
     * @param project The project for which to calculate the completion rate.
     * @return The completion rate of the project as a percentage.
     */
    public double completedRate(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("Project cannot be null.");
        }

        List<Task> tasks = project.getTasks();
        int total = project.getTaskCount();

        if (tasks == null || total == 0) {
            return 0.00;
        }

        int completed = 0;

        for (int i = 0; i < total; i++) {
            Task t = tasks.get(i);
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
