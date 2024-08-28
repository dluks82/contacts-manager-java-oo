package dev.dluks.contactsmanager.model;

import java.util.Objects;

public class User {
    private Long id;
    private String name;
    private String username;
    private String passwordHash;

    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.passwordHash = passwordToHash(password);
    }

    public User(Long id, String name, String username, String passwordHash) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
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
                && Objects.equals(username, user.username)
                && Objects.equals(passwordHash, user.passwordHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, username, passwordHash);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
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
