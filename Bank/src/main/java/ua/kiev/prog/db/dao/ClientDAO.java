package ua.kiev.prog.db.dao;

import ua.kiev.prog.db.entity.ClientEntity;

import javax.persistence.EntityManager;

public class ClientDAO extends AbstractDAO<ClientEntity> {

    public ClientDAO(EntityManager em) {
        super(em, ClientEntity.class);
    }
}
