package edu.asu.diging.monitor.core.db.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import edu.asu.diging.monitor.core.db.ITagDbConnection;
import edu.asu.diging.monitor.core.exceptions.UnstorableObjectException;
import edu.asu.diging.monitor.core.model.ITag;
import edu.asu.diging.monitor.core.model.impl.Tag;

@Component
@Transactional
public class TagDbConnection implements ITagDbConnection {

    @PersistenceContext
    private EntityManager em;

    @Override
    public ITag getById(String id) {
        return em.find(Tag.class, id);
    }

    @Override
    public Tag getTagByName(String name) {
        TypedQuery<Tag> query = em.createQuery("SELECT t FROM Tag t WHERE t.name = :name", Tag.class);
        query.setParameter("name", name);
        List<Tag> results = query.getResultList();
        if (results == null || results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }

    @Override
    public boolean store(List<Tag> tags) throws UnstorableObjectException {
        if (!tags.isEmpty()) {
            for (Tag tag : tags) {
                if (tag.getId() == null) {
                    throw new UnstorableObjectException("The tag " + tag.getName() + " does not have an id");
                }
                em.persist(tag);
            }
        }
        em.flush();
        return true;
    }

    @Override
    public void delete(ITag tag) {
        em.remove(tag);
        em.flush();
    }

    @Override
    public List<Tag> getAllTags(String userQuery) {
        TypedQuery<Tag> query = em.createQuery("SELECT t FROM Tag t WHERE t.name LIKE :userQuery", Tag.class);
        query.setParameter("userQuery", "%" + userQuery + "%");
        List<Tag> results = query.getResultList();
        if (results == null) {
            return Collections.emptyList();
        }
        return results;
    }

}
