package dev.dluks.contactsmanager.ui;

public class Header {
    public static void show(String loggedInName) {
        String loggedInMessage;
        if (loggedInName != null) {
            loggedInMessage = String.format("Logged in User: %-14.14s", loggedInName);
        } else {
            loggedInMessage = String.format("%-30.30s", "");
        }

        System.out.printf("""
                ╔═════════════════════════════════════════════════════════════════════════════════════╗
                ║   Welcome to                                                                        ║
                ║   ____            _             _         __  __                                    ║
                ║  / ___|___  _ __ | |_ __ _  ___| |_ ___  |  \\/  | __ _ _ __   __ _  __ _  ___ _ __  ║
                ║ | |   / _ \\| '_ \\| __/ _` |/ __| __/ __| | |\\/| |/ _` | '_ \\ / _` |/ _` |/ _ \\ '__| ║
                ║ | |__| (_) | | | | || (_| | (__| |_\\__ \\ | |  | | (_| | | | | (_| | (_| |  __/ |    ║
                ║  \\____\\___/|_| |_|\\__\\__,_|\\___|\\__|___/ |_|  |_|\\__,_|_| |_|\\__,_|\\__, |\\___|_|    ║
                ║                                                                    |___/            ║
                ║   %s                                     Versão: 2.0.0  ║
                ╚═════════════════════════════════════════════════════════════════════════════════════╝
                """, loggedInMessage);
    }
}
