package edu.asu.diging.monitor.core.db.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.asu.diging.monitor.core.db.IGroupDbConnection;
import edu.asu.diging.monitor.core.exceptions.UnstorableObjectException;
import edu.asu.diging.monitor.core.model.IApp;
import edu.asu.diging.monitor.core.model.impl.Group;

@Component
@Transactional
public class GroupDbConnection implements IGroupDbConnection {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private AppDbConnection appDbConn;

    @Override
    public List<Group> getAllGroups() {
        TypedQuery<Group> query = em.createQuery("SELECT a FROM Group a", Group.class);
        List<Group> results = query.getResultList();
        if (results == null) {
            return new ArrayList<>();
        }
        return results;
    }

    @Override
    public String generateGroupId() {
        String id = null;
        while (true) {
            id = "GRP" + appDbConn.generateUniqueId();
            Object existingFile = getGroupById(id);
            if (existingFile == null) {
                break;
            }
        }
        return id;
    }

    @Override
    public Group getGroupById(String id) {
        return em.find(Group.class, id);
    }

    @Override
    public Group createGroup(Group group) throws UnstorableObjectException {
        if (group.getId() == null) {
            logger.error("Group doesn't have an id");
            throw new UnstorableObjectException("Group doesn't have an id");
        }
        em.persist(group);
        return group;
    }

    @Override
    public void update(Group group) {
        for (IApp app : group.getApps()) {
            app.setGroup(group);
            em.merge(app);
        }
        em.merge(group);
    }

    public void deleteGroupForApps(Group group) {
        for (IApp app : group.getApps()) {
            app.setGroup(null);
            em.merge(app);
        }
        em.flush();
    }

    @Override
    public void deleteGroup(Group group) {
        em.remove(em.contains(group) ? group : em.merge(group));
    }
}
