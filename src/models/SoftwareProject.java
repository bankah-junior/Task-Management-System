package models;

public class SoftwareProject extends Project {

    public SoftwareProject(int id, String name, String description, double budget, int teamSize) {
        super(id, name, description, budget, teamSize);
    }

    @Override
    public String getProjectDetails() {
        return "Software Project";
    }
}

