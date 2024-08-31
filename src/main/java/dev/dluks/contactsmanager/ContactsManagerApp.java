package dev.dluks.contactsmanager;

import dev.dluks.contactsmanager.dao.ContactDAO;
import dev.dluks.contactsmanager.dao.UserDAO;
import dev.dluks.contactsmanager.db.DatabaseInitializer;
import dev.dluks.contactsmanager.db.H2FileDatabase;
import dev.dluks.contactsmanager.dto.*;
import dev.dluks.contactsmanager.model.Contact;
import dev.dluks.contactsmanager.service.ContactService;
import dev.dluks.contactsmanager.service.UserService;
import dev.dluks.contactsmanager.ui.*;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class ContactsManagerApp {
    static final int COLUNA_ID_WIDTH = 2;
    static final int COLUNA_NOME_WIDTH = 27;
    static final int COLUNA_TELEFONE_WIDTH = 20;
    static final int COLUNA_EMAIL_WIDTH = 25;

    private static boolean isLogged;
    private static Long id;
    private static String name;
    private static UserService userService;
    private static ContactService contactService;

    public static void main(String[] args) {
        H2FileDatabase database;
        Connection connection;
        UserDAO userDAO;
        ContactDAO contactDAO;

        try {
            database = new H2FileDatabase();
            connection = database.getConnection();
            DatabaseInitializer.execute(database);

            userDAO = new UserDAO(connection);
            userService = new UserService(userDAO);

            contactDAO = new ContactDAO(connection);
            contactService = new ContactService(contactDAO);

        } catch (Exception e) {
            System.err.println("Error connecting to the database!");
            System.exit(1);
        }

        Scanner scanner = new Scanner(System.in);

        boolean keepRunning = true;

        while (keepRunning) {
            if (!isLogged) {
                Screen.clear();
                Header.show(name);
                UnloggedMenu.show();

                int inputOption = Input.getAsInt("Enter the option: ", scanner, false);

                switch (inputOption) {
                    case 1 -> {
                        Screen.clear();
                        Header.show(name);
                        loginUser(scanner); // Logar}
                    }
                    case 2 -> {
                        Screen.clear();
                        Header.show(name);
                        registerUser(scanner); // Registrar
                    }

                    case 9 -> {
                        Screen.clear();
                        keepRunning = false;
                    }

                    default -> {
                        System.err.print("║   -> " + "Invalid option!");
                        scanner.nextLine(); // TODO: improve to use a function to pause
                    }
                }
            } else {
                Screen.clear();
                Header.show(name);
                LoggedMenu.show();
                int inputOption = Input.getAsInt("Enter the option: ", scanner, false);

                switch (inputOption) {
                    case 1 -> {
                        Screen.clear();
                        Header.show(name);
                        addContact(scanner);
                    }
                    case 2 -> {
                        Screen.clear();
                        Header.show(name);
                        contactDetails(scanner, id);
                        scanner.nextLine();
                    }
                    case 3 -> {
                        Screen.clear();
                        Header.show(name);
                        updateContact(scanner, id);
                        scanner.nextLine();
                    }
                    case 4 -> {
                        Screen.clear();
                        Header.show(name);
                        removeContact(scanner, id);
                        scanner.nextLine();
                    }
                    case 5 -> {
                        Screen.clear();
                        Header.show(name);
                        listAllContacts(id);
                        scanner.nextLine();
                    }
                    case 9 -> {
                        id = null;
                        name = null;
                        isLogged = false;
                    }
                    default -> {
                        System.err.print("║   -> " + "Invalid option!");
                        scanner.nextLine(); // TODO: improve to use a function to pause
                    }
                }
            }
        }
        scanner.close();
    }

    private static void updateContact(Scanner scanner, Long id) {
        System.out.println("║   Update Contact");
        Long contactId = Input.getAsLong("Enter contact ID: ", scanner, false);

        Contact current = contactService.getById(contactId, id);

        if (current != null) {

            String name = Input.getAsString("Enter new name [" + current.getName() + "]: ", scanner, true);
            String phone = Input.getAsString("Enter new phone number [" + current.getPhone() + "]: ", scanner, true);
            String email = Input.getAsString("Enter new email [" + current.getEmail() + "]: ", scanner, true);

            Contact updated = new Contact(
                    current.getId(),
                    name.trim().isEmpty() ? current.getName() : name,
                    phone.trim().isEmpty() ? current.getPhone() : phone,
                    email.trim().isEmpty() ? current.getEmail() : email,
                    current.getUser_id()
            );

            int updatedRows = contactService.update(updated);
            if (updatedRows > 0) {
                System.out.println("║   -> " + "Contact updated successfully.");
            }

        } else {
            System.out.println("║   -> " + "Contact not found!");
        }

    }

    private static void removeContact(Scanner scanner, Long id) {
        System.out.println("║   Remove Contact");
        Long contactId = Input.getAsLong("Enter contact ID: ", scanner, false);

        if (contactService.getById(contactId, id) != null) {

            int deletedRows = contactService.delete(contactId, id);

            if (deletedRows > 0) {
                System.out.println("║   -> " + "Contact removed successfully.");
            }
        } else {
            System.out.println("║   -> " + "Contact not found!");
        }
    }

    static void listAllContacts(Long id) {
        String format = "║ %-" + COLUNA_ID_WIDTH + "s | %-" + COLUNA_NOME_WIDTH + "s ║%n";

        System.out.println("║   Contact List");

        System.out.printf(format, "ID", "Nome");
        System.out.printf("║-%s-+-%s-║%n",
                "-".repeat(COLUNA_ID_WIDTH),
                "-".repeat(COLUNA_NOME_WIDTH));

        List<Contact> contacts = contactService.getAll(id);

        if (!contacts.isEmpty()) {
            for (Contact contact : contacts) {
                System.out.printf(format, contact.getId(), contact.getName());
            }
        } else {
            System.out.println("║   -> " + "Your contact list is empty.");
        }

        System.out.printf("╚-%s-+-%s-╝%n",
                "-".repeat(COLUNA_ID_WIDTH),
                "-".repeat(COLUNA_NOME_WIDTH));
    }

    static void contactDetails(Scanner scanner, Long userId) {
        System.out.println("║   View Contact Details");
        Long contactId = Input.getAsLong("Enter contact ID: ", scanner, false);

        Contact contact = contactService.getById(contactId, userId);

        if (contact != null) {
            String format = "║ %-" + COLUNA_ID_WIDTH + "s | %-" + COLUNA_NOME_WIDTH + "s | %-" + COLUNA_TELEFONE_WIDTH + "s | %-" + COLUNA_EMAIL_WIDTH + "s %n";

            System.out.printf(format, "ID", "Nome", "Telefone", "Email");
            System.out.printf("║-%s-+-%s-+-%s-+-%s-║%n",
                    "-".repeat(COLUNA_ID_WIDTH),
                    "-".repeat(COLUNA_NOME_WIDTH),
                    "-".repeat(COLUNA_TELEFONE_WIDTH),
                    "-".repeat(COLUNA_EMAIL_WIDTH));

            System.out.printf(format,
                    contact.getId(),
                    contact.getName(),
                    contact.getPhone(),
                    contact.getEmail());

            System.out.printf("╚-%s-+-%s-+-%s-+-%s-╝%n",
                    "-".repeat(COLUNA_ID_WIDTH),
                    "-".repeat(COLUNA_NOME_WIDTH),
                    "-".repeat(COLUNA_TELEFONE_WIDTH),
                    "-".repeat(COLUNA_EMAIL_WIDTH));
        } else {
            System.out.println("║   -> " + "Contact not found!");
        }
    }

    private static void loginUser(Scanner scanner) {
        System.out.println("║   Login");

        String username = Input.getAsString("Enter your username: ", scanner, false);
        String password = Input.getAsString("Enter your password: ", scanner, false);

        LoginRequestDTO request = new LoginRequestDTO(username, password);
        LoginResponseDTO response = userService.login(request);

        if (response != null && response.logged()) {
            id = response.id();
            name = response.name();
            isLogged = true;
        } else if (response != null) {
            System.err.println("║   -> " + response.errorMessage());
            scanner.nextLine(); // TODO: improve to use a function to pause
        } else {
            scanner.nextLine(); // TODO: improve to use a function to pause
        }
    }

    private static void registerUser(Scanner scanner) {
        System.out.println("║   Register New User");

        String name = Input.getAsString("Enter your name: ", scanner, false);
        String username = Input.getAsString("Enter your username: ", scanner, false);
        String password = Input.getAsString("Enter your password: ", scanner, false);

        RegisterUserRequestDTO request = new RegisterUserRequestDTO(name, username, password);

        RegisterUserResponseDTO response = userService.register(request);

        if (response != null && response.created()) {
            System.out.println("║   -> " + "User successfully created!");
            scanner.nextLine(); // TODO: improve to use a function to pause
        } else if (response != null) {
            System.err.println("║   -> " + response.errorMessage());
            scanner.nextLine(); // TODO: improve to use a function to pause
        } else {
            System.err.println("║   -> " + "Error creating user!");
            scanner.nextLine(); // TODO: improve to use a function to pause
        }
    }

    private static void addContact(Scanner scanner) {
        System.out.println("║   Add New Contact");
        String name = Input.getAsString("Enter name: ", scanner, false);
        String phone = Input.getAsString("Enter phone number: ", scanner, false);
        String email = Input.getAsString("Enter email: ", scanner, true);

        AddContactRequestDTO request = new AddContactRequestDTO(name, phone, email, id);

        AddContactResponseDTO response = contactService.add(request);

        if (response != null && response.created()) {
            System.out.println("║   -> " + "Contact successfully created!");
            scanner.nextLine(); // TODO: improve to use a function to pause
        } else if (response != null) {
            System.err.println("║   -> " + response.errorMessage());
            scanner.nextLine(); // TODO: improve to use a function to pause
        } else {
            System.err.println("║   -> " + "Error creating contact!");
            scanner.nextLine(); // TODO: improve to use a function to pause
        }
    }


}
