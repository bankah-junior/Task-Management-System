package models;

public class AdminUser extends User {
    public AdminUser(String name, String email) {
        super(name, email, "ADMIN");
    }

    @Override
    public void displayPermissions() {
        System.out.println("Permissions: Full access, can modify/delete projects and tasks.");
    }
}
