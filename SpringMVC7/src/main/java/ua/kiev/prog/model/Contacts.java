package ua.kiev.prog.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Contacts {
    @XmlElement(name = "contact")
    private List<Contact> listContacts = new ArrayList<>();

    public Contacts() {
    }

    public Contacts(List<Contact> listContacts) {
        this.listContacts = listContacts;
    }
}
