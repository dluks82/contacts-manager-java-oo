package dev.dluks.contactsmanager.ui;

import java.util.Scanner;

public class Input {
    public static int getAsInt(String promptMessage, Scanner scanner, boolean canBeNegative) {
        while (true) {
            System.out.print(promptMessage);
            try {
                int value = Integer.parseInt(scanner.nextLine());
                if (value >= 0 || canBeNegative) {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid value!");
            }
        }
    }

    public static double getAsDouble(String promptMessage, Scanner scanner, boolean canBeNegative) {
        while (true) {
            System.out.print(promptMessage);
            try {
                double value = Double.parseDouble(scanner.nextLine());
                if (value >= 0 || canBeNegative) {
                    return value;
                }
                System.out.println("Cannot be negative!");
            } catch (NumberFormatException e) {
                System.out.println("Invalid value!");
            }
        }
    }

    public static String getAsString(String promptMessage, Scanner scanner, boolean canBeEmpty) {
        while (true) {
            System.out.print(promptMessage);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty() || canBeEmpty) {
                return value;
            }
            System.out.println("Input cannot be empty!");
        }
    }

}
