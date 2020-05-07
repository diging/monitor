package edu.asu.diging.monitor.core.db.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import edu.asu.diging.monitor.core.auth.IUser;
import edu.asu.diging.monitor.core.auth.impl.User;
import edu.asu.diging.monitor.core.db.IUserDbConnection;
import edu.asu.diging.monitor.core.exceptions.UnstorableObjectException;

@Component
@Transactional
public class UserDbConnection implements IUserDbConnection {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public  IUser getById(String id) {
        return em.find(User.class, id);
    }
    
    @Override
    public IUser store(IUser user) throws UnstorableObjectException {
        em.persist(user);
        em.flush();
        return user;
    }
}
