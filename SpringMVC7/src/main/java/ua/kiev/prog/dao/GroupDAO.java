package ua.kiev.prog.dao;

import ua.kiev.prog.model.Contact;
import ua.kiev.prog.model.Group;

import java.util.List;

public interface GroupDAO {
    void add(Group group);
    void delete(Group group);
    void delete(long[] ids);
    Group findOne(long id);
    List<Group> list();
    List<Group> list(String pattern);

}
