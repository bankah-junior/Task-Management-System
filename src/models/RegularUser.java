package models;

public class RegularUser extends User {
    public RegularUser(String name, String email) {
        super(name, email, "REGULAR_USER");
    }

    @Override
    public void displayPermissions() {
        System.out.println("Permissions: Can view projects, add tasks, update own tasks.");
    }
}
