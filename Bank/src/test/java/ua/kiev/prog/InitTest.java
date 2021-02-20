package ua.kiev.prog;

import org.junit.Test;
import ua.kiev.prog.db.dao.ClientDAO;
import ua.kiev.prog.db.dao.DAO;
import ua.kiev.prog.db.entity.ClientEntity;

import static org.junit.Assert.assertEquals;

public class InitTest extends BaseTest {

    @Test
    public void addClient(){
        DAO<Integer, ClientEntity> clientDAO = new ClientDAO(em);
        ClientEntity clientEntity = new ClientEntity("Mark");

        clientDAO.insert(clientEntity);


     }
}
