package dev.dluks.contactsmanager.ui;

public class LoggedMenu {
    public static void show(String name) {
        System.out.println("Logged in User: " + name);
        System.out.println("""
                [9] - Logout
                """);
    }
}
