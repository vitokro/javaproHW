package ua.kiev.prog.model;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

@Entity
@Table(name="Contacts")
@XmlRootElement(name = "contact")
public class Contact {
    @Id
    @GeneratedValue
    private Long id;
    
    @ManyToOne
    @JoinColumn(name="group_id")
    private Group group;

    private String name;
    private String surname;
    private String phone;
    private String email;

    public Contact() {}

    public Contact(Group group, String name, String surname, String phone, String email) {
        this.group = group;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public Group getGroup() {
        return group;
    }

    @XmlElement(name = "group")
    public String getGroupStr() {
        if (group == null)
            return "Default";
        return group.getName();
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
