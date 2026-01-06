package com.amalitech.utils.menus;

import com.amalitech.utils.ValidationUtils;

import java.util.Scanner;

public class GetMenuChoice {
    private final Scanner scanner;

    public GetMenuChoice(Scanner scanner) {
        this.scanner = scanner;
    }

    public int getMenuChoice(int maxRange) {
        int choice;
        while (true) {
            System.out.print("Enter choice: ");
            String input = scanner.nextLine();
            if (ValidationUtils.isInteger(input)) {
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > maxRange) System.out.println("Invalid option! \nChoice must be " + 1 + "-" + maxRange + ".");
                else break;
            } else {
                System.out.println("Enter a valid number!");
                continue;
            }
        }
        return choice;
    }
}
