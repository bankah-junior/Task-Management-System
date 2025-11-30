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

    public List<User> getUsers() {
        return users;
    }

    public User createUser(String name, String email, String role) {
        User user = null;
        if ("ADMIN".equalsIgnoreCase(role)) {
            user = new AdminUser(name, email);
        } else if ("REGULAR_USER".equalsIgnoreCase(role)) {
            user = new RegularUser(name, email);
        } else {
            System.out.println("Enter a valid role!!!");
            return user;
        }
        users.add(user);
        System.out.println("User created: " + user);
        return user;
    }

    public void displayUsers() {
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        System.out.println("---------------------------------------------------------------");
        System.out.println("ID    | Name            | Email                     | Role");
        System.out.println("---------------------------------------------------------------");

        for (User u : users) {
            System.out.println(u);
        }

        System.out.println("---------------------------------------------------------------");
    }

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

    public void assignUserToProject(User user, Project project) {
        if (user != null && project != null) {
            user.assignProject(project);
            System.out.println("User " + user.getName() + " assigned to project " + project.getName());
        }
    }

    public void assignUserToTask(User user, Task task) {
        if (user != null && task != null) {
            user.assignTask(task);
            task.setAssignedUser(user);
            System.out.println("User " + user.getName() + " assigned to task " + task.getName());
        }
    }

    public User login(int userId) {
        for (User u : users) {
            if (u.getId() == userId) {
                return u;
            }
        }
        System.out.println("User ID not found.");
        return null;
    }
}
