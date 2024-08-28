package dev.dluks.contactsmanager;

import dev.dluks.contactsmanager.dao.UserDAO;
import dev.dluks.contactsmanager.db.DatabaseInitializer;
import dev.dluks.contactsmanager.db.H2FileDatabase;
import dev.dluks.contactsmanager.dto.LoginRequestDTO;
import dev.dluks.contactsmanager.dto.LoginResponseDTO;
import dev.dluks.contactsmanager.dto.RegisterUserRequestDTO;
import dev.dluks.contactsmanager.dto.RegisterUserResponseDTO;
import dev.dluks.contactsmanager.service.UserService;
import dev.dluks.contactsmanager.ui.*;

import java.sql.Connection;
import java.util.Scanner;

public class ContactsManagerApp {
    private static boolean isLogged;
    private static Long id;
    private static String name;
    private static UserService userService;

    public static void main(String[] args) {
        H2FileDatabase database;
        Connection connection;
        UserDAO userDAO;

        try {
            database = new H2FileDatabase();
            connection = database.getConnection();
            DatabaseInitializer.execute(database);

            userDAO = new UserDAO(connection);
            userService = new UserService(userDAO);

        } catch (Exception e) {
            System.err.println("Error connecting to the database!");
            System.exit(1);
        }

        Scanner scanner = new Scanner(System.in);

        boolean keepRunning = true;

        while (keepRunning) {
            Screen.clear();
            Header.show();

            if (isLogged) {
                LoggedMenu.show(name);
                int inputOption = Input.getAsInt("Enter the option: ", scanner, false);

                switch (inputOption) {
                    case 9 -> {
                        id = null;
                        name = null;
                        isLogged = false;
                    }
                    default -> System.out.println("Invalid option!");
                }

            } else {
                UnloggedMenu.show();
                int inputOption = Input.getAsInt("Enter the option: ", scanner, false);

                switch (inputOption) {
                    case 1 -> logarUsuario(scanner); // Logar

                    case 2 -> registrarUsuario(scanner); // Registrar

                    case 9 -> keepRunning = false;

                    default -> System.out.println("Invalid option!");
                }
            }
        }

        scanner.close();
    }

    private static void logarUsuario(Scanner scanner) {
        String username = Input.getAsString("Enter your username: ", scanner, false);
        String password = Input.getAsString("Enter your password: ", scanner, false);

        LoginRequestDTO request = new LoginRequestDTO(username, password);
        LoginResponseDTO response = userService.login(request);

        if (response != null && response.logged()) {
            id = response.id();
            name = response.name();
            isLogged = true;
        } else if (response != null) {
            System.out.println(response.errorMessage());
        } else {
            System.out.println("Error logging in!");
        }
    }

    private static void registrarUsuario(Scanner scanner) {

        String name = Input.getAsString("Enter your name: ", scanner, false);
        String username = Input.getAsString("Enter your username: ", scanner, false);
        String password = Input.getAsString("Enter your password: ", scanner, false);

        RegisterUserRequestDTO request = new RegisterUserRequestDTO(name, username, password);

        RegisterUserResponseDTO response = userService.register(request);

        if (response != null && response.created()) {
            System.out.println("User successfully created!");
        } else if (response != null) {
            System.out.println(response.errorMessage());
        } else {
            System.out.println("Error creating user!");
        }
    }

}
