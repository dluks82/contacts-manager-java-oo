package dev.dluks.contactsmanager.ui;

public class UnloggedMenu {
    public static void show() {
        String loginOption = prepareOptionLine("[1] - Login");
        String registerOption = prepareOptionLine("[2] - Register");
        String exitOption = prepareOptionLine("[9] - Exit Application");

        String topLine = "╔" + "═".repeat(85) + "╗";
        String emptyLine = "║" + " ".repeat(85) + "║";
        String bottonLine = "╚" + "═".repeat(85) + "╝";

        System.out.println(emptyLine);
        System.out.println(loginOption);
        System.out.println(registerOption);
        System.out.println(emptyLine);
        System.out.println(exitOption);
        System.out.println(emptyLine);

        System.out.println(bottonLine);
    }

    private static String prepareOptionLine(String optionText) {
        return String.format("║   %-81.81s ║", optionText);
    }
}
