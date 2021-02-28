package ua.kiev.prog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.kiev.prog.model.Contact;
import ua.kiev.prog.model.Group;
import ua.kiev.prog.services.ContactService;
import ua.kiev.prog.services.ContactServiceImpl;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class TestDataBean {
    @Autowired
    private ContactService contactService;

    @PostConstruct
    public void fillData() {
        List<Group> groups = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Group group = new Group("Test" + i);
            groups.add(group);
            contactService.addGroup(group);
        }

        Contact contact;

        for (int i = 0; i < 25; i++) {
            contact = new Contact(null, "Name" + i, "Surname" + i, "1234567" + i, "user" + i + "@test.com");
            contactService.addContact(contact);
        }
        for (int i = 0; i < 12; i++) {
            contact = new Contact(groups.get(new Random().nextInt(5)), "Other" + i, "OtherSurname" + i, "7654321" + i, "user" + i + "@other.com");
            contactService.addContact(contact);
        }
    }
}
