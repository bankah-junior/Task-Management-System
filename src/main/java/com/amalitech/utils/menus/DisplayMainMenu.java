package com.amalitech.utils.menus;

import com.amalitech.models.User;

public class DisplayMainMenu {
    private User loggedInUser;

    public DisplayMainMenu(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void displayMainMenu() {
        System.out.println("\n======================================");
        System.out.println("||  JAVA PROJECT MANAGEMENT SYSTEM  ||");
        System.out.println("======================================");
        System.out.println("\nCurrent User: " + loggedInUser.getName() + " (" + loggedInUser.getRole() + ")");
        System.out.println("\nMain Menu");
        System.out.println("---------");
        System.out.println("1. Manage Project");
        System.out.println("2. Manage Tasks");
        System.out.println("3. Manage Users");
        System.out.println("4. View Status Reports");
        System.out.println("5. Switch User");
        System.out.println("6. Exit");
    }
}
