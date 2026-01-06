package com.amalitech.concurrency;

import com.amalitech.services.ProjectService;
import com.amalitech.services.TaskService;
import com.amalitech.services.UserService;

/**
 * Background task that periodically saves application data.
 */
public class AutoSaveTask implements Runnable {

    private final UserService userService;
    private final ProjectService projectService;
    private final TaskService taskService;
    private volatile boolean running = true;

    public AutoSaveTask(UserService us, ProjectService ps, TaskService ts) {
        this.userService = us;
        this.projectService = ps;
        this.taskService = ts;
    }

    /**
     * Stops the auto-save thread.
     */
    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(60_000); // every 60 seconds (1 minute)
                userService.saveUsers();
                projectService.saveProjects();
                taskService.saveTasks(projectService.getProjects());
//                System.out.println("[AutoSave] Data persisted.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("AutoSave interrupted.");
            }
        }
    }
}
