package dev.dluks.contactsmanager.ui;

import java.util.Scanner;

public class Input {
    static String emptyLine = "║";
    public static int getAsInt(String promptMessage, Scanner scanner, boolean canBeNegative) {
        while (true) {
            System.out.println(emptyLine);
            System.out.print("║   -> " + promptMessage);
            try {
                int value = Integer.parseInt(scanner.nextLine());
                if (value >= 0 || canBeNegative) {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.err.print("║   -> " + "Invalid value!");
                scanner.nextLine(); // TODO: improve later
            }
        }
    }

    public static double getAsDouble(String promptMessage, Scanner scanner, boolean canBeNegative) {
        while (true) {
            System.out.println(emptyLine);
            System.out.print("║   -> " + promptMessage);
            try {
                double value = Double.parseDouble(scanner.nextLine());
                if (value >= 0 || canBeNegative) {
                    return value;
                }
                System.err.print("║   -> " + "Cannot be negative!");
                scanner.nextLine(); // TODO: improve later

            } catch (NumberFormatException e) {
                System.err.print("║   -> " + "Invalid value!");
                scanner.nextLine(); // TODO: improve later
            }
        }
    }

    public static long getAsLong(String promptMessage, Scanner scanner, boolean canBeNegative) {
        while (true) {
            System.out.println(emptyLine);
            System.out.print("║   -> " + promptMessage);
            try {
                long value = Long.parseLong(scanner.nextLine());
                if (value >= 0 || canBeNegative) {
                    return value;
                }
                System.err.print("║   -> " + "Cannot be negative!");
                scanner.nextLine(); // TODO: improve later

            } catch (NumberFormatException e) {
                System.err.print("║   -> " + "Invalid value!");
                scanner.nextLine(); // TODO: improve later
            }
        }
    }

    public static String getAsString(String promptMessage, Scanner scanner, boolean canBeEmpty) {
        while (true) {
            System.out.println(emptyLine);
            System.out.print("║   -> " + promptMessage);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty() || canBeEmpty) {
                return value;
            }
            System.err.print("║   -> " + "Input cannot be empty!");
            scanner.nextLine(); // TODO: improve later
        }
    }

}
