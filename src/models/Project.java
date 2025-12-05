package models;

public abstract class Project {

    private int id;
    private String name;
    private String description;
    private double budget;
    private int teamSize;

    private Task[] tasks = new Task[20];
    private int taskCount = 0;

    public Project(int id, String name, String description, double budget, int teamSize) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.budget = budget;
        this.teamSize = teamSize;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getBudget() {
        return budget;
    }

    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setTeamSize(int teamSize) { this.teamSize = teamSize; }
    public void setBudget(double budget) { this.budget = budget; }

    public abstract String getProjectDetails();

    public void displayProject() {
        System.out.println("\n--------------------------------------------------");
        System.out.println("Project ID   : " + id);
        System.out.println("Name         : " + name);
        System.out.println("Team Size    : " + teamSize);
        System.out.println("Budget       : $" + budget);
        System.out.println("Description  : " + description);
        System.out.println("Type         : " + getProjectDetails());
        System.out.println("--------------------------------------------------");
    }
    public void displayProjectHorizontal() {
        System.out.printf(
                "%-4d | %-20s | %-15s | %-10d | $%-10.2f | %s\n",
                id,
                name,
                getProjectDetails(),
                teamSize,
                budget,
                description
        );
    }

    public boolean addTask(Task task) {
        // prevent duplicate IDs
        for (int i = 0; i < taskCount; i++) {
            if (tasks[i].getId() == task.getId()) {
                System.out.println("Task with ID " + task.getId() + " already exists!");
                return false;
            }
        }

        if (taskCount < tasks.length) {
            tasks[taskCount++] = task;
            System.out.printf("Task \"%s\" added successfully to Project %s\n", task.getName(), id);
            return true;
        } else {
            System.out.println("Task list is full!");
            return false;
        }
    }
    public Task getTaskById(int id) {
        for (int i = 0; i < taskCount; i++) {
            if (tasks[i].getId() == id) {
                return tasks[i];
            }
        }
        return null;
    }
    public void displayAllTasks() {
        if (taskCount == 0) {
            System.out.println("No tasks found for this project.");
            return;
        }

        System.out.println("\n---------------------------------------");
        System.out.println("Tasks for Project: " + getName());
        System.out.println("---------------------------------------");

        System.out.println("--------------------------------------------------");
        System.out.println("ID   | TASK NAME            | STATUS");
        System.out.println("--------------------------------------------------");
        for (int i = 0; i < taskCount; i++) {
            tasks[i].displayTask();
        }
        System.out.println("--------------------------------------------------");
        System.out.println("Total Tasks: " + taskCount);
        System.out.println("--------------------------------------------------");
    }
    public Task[] getTasks() {
        return tasks;
    }
    public int getTaskCount() {
        return taskCount;
    }
    public void decrementTaskCount() {
        if (taskCount > 0) taskCount--;
    }
}
