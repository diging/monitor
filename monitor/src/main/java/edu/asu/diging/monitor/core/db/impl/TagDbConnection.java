package edu.asu.diging.monitor.core.db.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import edu.asu.diging.monitor.core.db.ITagDbConnection;
import edu.asu.diging.monitor.core.model.ITag;

@Component
@Transactional
public class TagDbConnection implements ITagDbConnection {

    @PersistenceContext
    private EntityManager em;

    @Override
    public ITag getById(String id) {
        return em.find(ITag.class, id);
    }

    @Override
    public ITag getTagByName(String name) {
        TypedQuery<ITag> query = em.createQuery("SELECT t FROM Tag t WHERE t.name = :name", ITag.class);
        query.setParameter("name", name);
        List<ITag> results = query.getResultList();
        if (results == null || results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }

    @Override
    public List<ITag> getAllTags(String userQuery) {
        TypedQuery<ITag> query = em.createQuery("SELECT t FROM Tag t WHERE t.name LIKE :userQuery", ITag.class);
        query.setParameter("userQuery", "%" + userQuery + "%");
        List<ITag> results = query.getResultList();
        if (results == null) {
            return Collections.emptyList();
        }
        return results;
    }

}
