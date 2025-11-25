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

        double percent = (total == 0) ? 0.0 : ((double) completed / total) * 100;
        percent = Math.round(percent * 100) / 100.0;

        return new StatusReport(total, completed, pending, percent, userTaskSummary);
    }
}
