package com.amalitech.models;

import java.util.Map;

public class StatusReport {
    private int totalTasks;
    private int completedTasks;
    private int pendingTasks;
    private double percentageCompleted;

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
    public double getPercentageCompleted() { return percentageCompleted; }

}
