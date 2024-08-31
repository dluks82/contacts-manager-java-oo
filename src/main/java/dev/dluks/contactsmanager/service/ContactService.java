package dev.dluks.contactsmanager.service;

import dev.dluks.contactsmanager.dao.ContactDAO;
import dev.dluks.contactsmanager.dto.AddContactRequestDTO;
import dev.dluks.contactsmanager.dto.AddContactResponseDTO;
import dev.dluks.contactsmanager.model.Contact;

import java.sql.SQLException;
import java.util.List;

public class ContactService {

    private final ContactDAO contactDAO;

    public ContactService(ContactDAO contactDAO) {
        this.contactDAO = contactDAO;
    }

    public AddContactResponseDTO add(AddContactRequestDTO data) {
        // TODO: verify dups

        Contact newContact = new Contact(data.name(), data.phone(), data.email(), data.userId());

        try {
            if (contactDAO.findByPhoneAndUserId(data.phone(), data.userId()) != null) {
                return new AddContactResponseDTO(false, null, "Phone number already registered");
            }

            if (contactDAO.findByEmailAndUserId(data.email(), data.userId()) != null) {
                return new AddContactResponseDTO(false, null, "Email already registered");
            }

            Long generatedId = contactDAO.save(newContact);

            if (generatedId > 0) {
                return new AddContactResponseDTO(true, generatedId, null);
            }
            return new AddContactResponseDTO(false, null, "Unexpected Error!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int delete(Long id, Long userId) {
        Contact contact = getById(id, userId);
        if (contact != null) {
            return contactDAO.remove(contact);
        }
        return 0;
    }

    public int update(Contact updatedContact) {
        Contact oldContact = getById(updatedContact.getId(), updatedContact.getUser_id());
        updatedContact.setName(updatedContact.getName().trim().isEmpty() ? oldContact.getName() : updatedContact.getName());
        updatedContact.setPhone(updatedContact.getPhone().trim().isEmpty() ? oldContact.getPhone() : updatedContact.getPhone());
        updatedContact.setEmail(updatedContact.getEmail().trim().isEmpty() ? oldContact.getEmail() : updatedContact.getEmail());

        return contactDAO.update(updatedContact);

    }

    public List<Contact> getAll(Long id) {
        return contactDAO.findAll(id);
    }

    public Contact getById(Long id, Long userId) {
        return contactDAO.findById(id, userId);
    }

    public Contact getByEmail(String email, Long userId) {
        return contactDAO.findByEmailAndUserId(email, userId);
    }

    public Contact getByPhone(String phone, Long userId) {
        return contactDAO.findByPhoneAndUserId(phone, userId);
    }

}
