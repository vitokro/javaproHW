package ua.kiev.prog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kiev.prog.dao.ContactDAO;
import ua.kiev.prog.dao.GroupDAO;
import ua.kiev.prog.model.Contact;
import ua.kiev.prog.model.Contacts;
import ua.kiev.prog.model.Group;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    private ContactDAO contactDAO;
    @Autowired
    private GroupDAO groupDAO;

    @Transactional //(rollbackFor = RuntimeException.class)
    public void addContact(Contact contact) {
        contactDAO.add(contact);
    }

    @Transactional
    public void addGroup(Group group) {
        groupDAO.add(group);
    }

    @Transactional
    public void deleteContact(long[] ids) {
        contactDAO.delete(ids);
    }

    @Override
    public void saveContact(List<Long> toSave) {
        final List<Contact> list = contactDAO.list(toSave);
        saveToXMLFile(list);
    }

    private void saveToXMLFile(List<Contact> list) {
        final File file = new File("Contacts.xml");
        Contacts contacts = new Contacts(list);
        try {
            JAXBContext jaxbC = JAXBContext.newInstance(Contacts.class);
            Marshaller marSh = jaxbC.createMarshaller();
            marSh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marSh.marshal(contacts, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

    @Transactional
    public void deleteGroups(long[] ids) {

        groupDAO.delete(ids);
    }

    @Transactional(readOnly = true)
    public List<Group> listGroups() {
        return groupDAO.list();
    }

    @Transactional(readOnly = true)
    public List<Contact> listContacts(Group group, int start, int count) {
        return contactDAO.list(group, start, count);
    }

    @Transactional(readOnly = true)
    public List<Contact> listContacts(Group group) {
        return contactDAO.list(group, 0, 0);
    }

    @Transactional(readOnly = true)
    public long count() {
        return contactDAO.count();
    }

    @Transactional(readOnly = true)
    public Group findGroup(long id) {
        return groupDAO.findOne(id);
    }

    @Transactional(readOnly = true)
    public List<Contact> searchContacts(String pattern) {
        return contactDAO.list(pattern);
    }

    @Transactional(readOnly = true)
    public List<Group> searchGroups(String pattern) {
        return groupDAO.list(pattern);
    }
}
