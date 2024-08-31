package dev.dluks.contactsmanager.ui;

public class LoggedMenu {
    public static void show() {
        String addOption = prepareOptionLine("[1] - Add Contact");
        String viewOption = prepareOptionLine("[2] - View Contact Details");
        String updateOption = prepareOptionLine("[3] - Update Contact");
        String removeOption = prepareOptionLine("[4] - Remove Contact");
        String listOption = prepareOptionLine("[5] - List Contacts");
        String logoutOption = prepareOptionLine("[9] - Logout");

        String topLine = "╔" + "═".repeat(85) + "╗";
        String emptyLine = "║" + " ".repeat(85) + "║";
        String bottonLine = "╚" + "═".repeat(85) + "╝";

        System.out.println(emptyLine);
        System.out.println(addOption);
        System.out.println(viewOption);
        System.out.println(updateOption);
        System.out.println(removeOption);
        System.out.println(listOption);
        System.out.println(emptyLine);
        System.out.println(logoutOption);
        System.out.println(emptyLine);

        System.out.println(bottonLine);

    }

    private static String prepareOptionLine(String optionText) {
        return String.format("║   %-81.81s ║", optionText);
    }
}
