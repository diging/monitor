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
    public ITag store(ITag tag) throws UnstorableObjectException {
        if (tag.getId() == null) {
            throw new UnstorableObjectException("Tag does not have an id.");
        }
        if (tag.getName() != null) {
            em.persist(tag);
        }
        em.flush();
        return tag;
    }

    @Override
    public void delete(ITag tag) {
        em.remove(tag);
        em.flush();
    }

    @Override
    public List<ITag> getAllTags() {
        TypedQuery<ITag> query = em.createQuery("SELECT t FROM Tag t", ITag.class);
        List<ITag> results = query.getResultList();
        if (results == null) {
            return Collections.emptyList();
        }
        return results;
    }

}
