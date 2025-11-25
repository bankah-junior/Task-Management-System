package models;

import java.util.Map;

public class StatusReport {
    private int totalTasks;
    private int completedTasks;
    private int pendingTasks;
    private double percentageCompleted;

    // Optional: per-user completed tasks summary
    private Map<String, Integer> userTaskSummary;

    public StatusReport(int totalTasks, int completedTasks, int pendingTasks, double percentageCompleted) {
        this.totalTasks = totalTasks;
        this.completedTasks = completedTasks;
        this.pendingTasks = pendingTasks;
        this.percentageCompleted = percentageCompleted;
    }

    public StatusReport(int totalTasks, int completedTasks, int pendingTasks, double percentageCompleted, Map<String, Integer> userTaskSummary) {
        this(totalTasks, completedTasks, pendingTasks, percentageCompleted);
        this.userTaskSummary = userTaskSummary;
    }

    public int getTotalTasks() { return totalTasks; }
    public int getCompletedTasks() { return completedTasks; }
    public int getPendingTasks() { return pendingTasks; }
    public double getPercentageCompleted() { return percentageCompleted; }
    public Map<String, Integer> getUserTaskSummary() { return userTaskSummary; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Total Tasks   : ").append(totalTasks).append("\n");
        sb.append("Completed     : ").append(completedTasks).append("\n");
        sb.append("Pending       : ").append(pendingTasks).append("\n");
        sb.append("Progress (%)  : ").append(percentageCompleted).append("\n");

        if (userTaskSummary != null && !userTaskSummary.isEmpty()) {
            sb.append("Per-User Task Completion:\n");
            for (Map.Entry<String, Integer> entry : userTaskSummary.entrySet()) {
                sb.append(" - ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
        }

        return sb.toString();
    }
}
