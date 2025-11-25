package services;

import models.User;
import models.Project;
import models.Task;
import models.AdminUser;
import models.RegularUser;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private List<User> users;

    public UserService() {
        users = new ArrayList<>();
    }

    // Create user
    public User createUser(String name, String email, String role) {
        User user = null;
        if ("ADMIN".equalsIgnoreCase(role)) {
            user = new AdminUser(name, email);
        } else {
            user = new RegularUser(name, email);
        }
        users.add(user);
        System.out.println("User created: " + user);
        return user;
    }

    // View all users
    public void displayUsers() {
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }
        for (User u : users) {
            System.out.println(u);
        }
    }

    // Delete user by ID
    public boolean deleteUser(int userId) {
        for (User u : users) {
            if (u.getId() == userId) {
                users.remove(u);
                System.out.println("User deleted successfully.");
                return true;
            }
        }
        System.out.println("User ID not found.");
        return false;
    }

    // Assign user to project
    public void assignUserToProject(User user, Project project) {
        if (user != null && project != null) {
            user.assignProject(project);
            System.out.println("User " + user.getName() + " assigned to project " + project.getName());
        }
    }

    // Assign user to task
    public void assignUserToTask(User user, Task task) {
        if (user != null && task != null) {
            user.assignTask(task);
            task.setAssignedUser(user);
            System.out.println("User " + user.getName() + " assigned to task " + task.getName());
        }
    }

    // Simple login (by user ID)
    public User login(int userId) {
        for (User u : users) {
            if (u.getId() == userId) {
                System.out.println("Welcome, " + u.getName() + " [" + u.getRole() + "]");
                return u;
            }
        }
        System.out.println("User ID not found.");
        return null;
    }
}
