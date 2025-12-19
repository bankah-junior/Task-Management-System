package com.amalitech.utils.menus;

import com.amalitech.models.Project;
import com.amalitech.services.ProjectService;
import com.amalitech.services.ReportService;

import java.util.List;

public class ReportMenu {
    private final ReportService reportService;
    private final ProjectService projectService;

    public ReportMenu(ReportService reportService, ProjectService projectService) {
        this.reportService = reportService;
        this.projectService = projectService;
    }

    public void reportMenu() {
        System.out.println("\n======================================");
        System.out.println("||      PROJECT STATUS REPORT       ||");
        System.out.println("======================================");
        List<Project> allProjects = projectService.getProjects();
        reportService.generateAllProjectReports(allProjects);
    }
}
