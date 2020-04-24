package edu.asu.diging.monitor.core.db.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import edu.asu.diging.monitor.core.auth.User;
import edu.asu.diging.monitor.core.db.IUserDbConnection;
import edu.asu.diging.monitor.core.exceptions.UnstorableObjectException;

@Component
@Transactional
public class UserDbConnection implements IUserDbConnection {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public User getById(String id) {
        return em.find(User.class, id);
    }
    
    @Override
    public User store(User user) throws UnstorableObjectException {
        em.persist(user);
        em.flush();
        return user;
    }
}
