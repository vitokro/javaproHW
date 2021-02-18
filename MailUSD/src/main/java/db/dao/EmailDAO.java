package db.dao;

import db.entity.Email;

import javax.persistence.EntityManager;

public class EmailDAO  extends AbstractDAO<Integer, Email>{

    public EmailDAO(EntityManager em) {
        super(em, Email.class);
    }
}
