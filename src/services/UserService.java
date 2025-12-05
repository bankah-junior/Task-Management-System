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

    /**
     * Creates a new user with the specified name, email, and role.
     * @param name The name of the user.
     * @param email The email of the user.
     * @param role The role of the user (ADMIN or REGULAR_USER).
     * @return The created User object.
     */
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

    /**
     * Displays all users in the system.
     */
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

    /**
     * Deletes a user from the system.
     * @param userId The ID of the user to delete.
     * @return true if the user was successfully deleted, false otherwise.
     */
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

     /**
      * Assigns a user to a project.
      * @param user The user to assign to the project.
      * @param project The project to which the user will be assigned.
      */
    public void assignUserToProject(User user, Project project) {
        if (user != null && project != null) {
            user.assignProject(project);
            System.out.println("User " + user.getName() + " assigned to project " + project.getName());
        }
    }

     /**
      * Assigns a user to a task.
      * @param user The user to assign to the task.
      * @param task The task to which the user will be assigned.
      */
    public void assignUserToTask(User user, Task task) {
        if (user != null && task != null) {
            user.assignTask(task);
            task.setAssignedUser(user);
            System.out.println("User " + user.getName() + " assigned to task " + task.getName());
        }
    }

    /**
     * Logs in a user by their ID.
     * @param userId The ID of the user to log in.
     * @return The User object if found, null otherwise.
     */
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
