package dev.dluks.contactsmanager.model;

public class Contact {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private Long user_id;

    public Contact(String name, String phone, String email, Long userId) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        user_id = userId;
    }

    public Contact(Long id, String name, String phone, String email, Long userId) {
        this(name, phone, email, userId);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public Long getUser_id() {
        return user_id;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
