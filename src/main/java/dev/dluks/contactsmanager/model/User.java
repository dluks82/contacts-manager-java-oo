package dev.dluks.contactsmanager.model;

import java.util.Objects;

public class User {
    private Long id;
    private String name;
    private String login;
    private String passwordHash;

    public User(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.passwordHash = passwordToHash(password);
    }

    public User(Long id, String name, String login, String passwordHash) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.passwordHash = passwordHash;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id)
                && Objects.equals(name, user.name)
                && Objects.equals(login, user.login)
                && Objects.equals(passwordHash, user.passwordHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, login, passwordHash);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", password='" + passwordHash + '\'' + // TODO: refactor to remove this
                '}';
    }

    public boolean passwordMatch(String password) {
        return this.passwordHash.equals(passwordToHash(password));
    }

    private String passwordToHash(String password) {
        // TODO: Implement secure hashPassword logic
        return "$pwd" + password.trim() + "hashed"; // :) very secure!
    }
}
