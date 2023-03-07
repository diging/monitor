package edu.asu.diging.monitor.core.db.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import edu.asu.diging.monitor.core.db.ITagDbConnection;
import edu.asu.diging.monitor.core.exceptions.UnstorableObjectException;
import edu.asu.diging.monitor.core.model.ITag;
import edu.asu.diging.monitor.core.model.impl.Tag;

@Component
@Transactional
public class TagDbConnection implements ITagDbConnection {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PersistenceContext
    private EntityManager em;

    @Override
    public ITag getById(String id) {
        return em.find(Tag.class, id);
    }

    @Override
    public boolean store(List<Tag> tags) throws UnstorableObjectException {
        if (!tags.isEmpty()) {
            for (Tag tag: tags) {
                logger.debug("Tag name: ");
                logger.debug(tag.getName());
                if (tag.getId() == null) {
                    throw new UnstorableObjectException("The tag " + tag.getName() + " does not have an id");
                }
                em.persist(tag);
            }
        }
        em.flush();
        return true;
        
//        if (tag.getId() == null) {
//            throw new UnstorableObjectException("Tag does not have an id.");
//        }
//        if (tag.getName() != null) {
//            em.persist(tag);
//        }
//        em.flush();
//        return true;
    }
    
    @Override
    public void delete(ITag tag) {
        em.remove(tag);
        em.flush();
    }

    @Override
    public List<Tag> getAllTags() {
        TypedQuery<Tag> query = em.createQuery("SELECT t FROM Tag t", Tag.class);
        List<Tag> results = query.getResultList();
        if (results == null) {
            return Collections.emptyList();
        }
        return results;
    }

}
