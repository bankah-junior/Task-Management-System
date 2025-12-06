package com.amalitech.models;

public class HardwareProject extends Project {

    public HardwareProject(int id, String name, String description, double budget, int teamSize) {
        super(id, name, description, budget, teamSize);
    }

    @Override
    public String getProjectDetails() {
        return "Hardware Project";
    }
}

