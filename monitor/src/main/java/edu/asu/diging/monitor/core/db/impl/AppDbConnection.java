package edu.asu.diging.monitor.core.db.impl;

import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Component;

import edu.asu.diging.monitor.core.db.IAppDbConnection;
import edu.asu.diging.monitor.core.exceptions.GroupNotFoundException;
import edu.asu.diging.monitor.core.exceptions.UnstorableObjectException;
import edu.asu.diging.monitor.core.model.IApp;
import edu.asu.diging.monitor.core.model.INotificationRecipient;
import edu.asu.diging.monitor.core.model.impl.App;
import edu.asu.diging.monitor.core.model.impl.Group;

@Component
@Transactional
public class AppDbConnection implements IAppDbConnection {

    @PersistenceContext
    private EntityManager em;

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.asu.diging.monitor.core.db.impl.IAppDbConnection#getAppById(java.lang.
     * String)
     */
    @Override
    public IApp getById(String id) {
        return em.find(App.class, id);
    }

    @Override
    public Group getGroupById(String id) throws GroupNotFoundException {
        Group group = em.find(Group.class, id);
        if (group == null) {
            throw new GroupNotFoundException("No group exists for this id");
        }
        return em.find(Group.class, id);
    }

    @Override
    public Group createGroup(Group group) throws UnstorableObjectException {
        if (group.getName() == null) {
            throw new UnstorableObjectException("Group doesn't have an id");
        }
        em.persist(group);
        return group;
    }

    @Override
    public void deleteGroup(Group group) {
        em.remove(group);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.asu.diging.monitor.core.db.impl.IAppDbConnection#storeModifiedApp(edu.asu
     * .diging.monitor.core.model.IApp)
     */
    @Override
    public IApp store(IApp app) throws UnstorableObjectException {
        if (app.getId() == null) {
            throw new UnstorableObjectException("App does not have an id.");
        }
        if (app.getRecipients() != null) {
            for (INotificationRecipient recipient : app.getRecipients()) {
                recipient.getApps().add((App) app);
                em.merge(recipient);
            }
        } else {
            em.persist(app);
        }
        em.flush();
        return app;
    }

    @Override
    public IApp update(IApp app) {
        if (app.getRecipients() != null && app.getRecipients().size() != 0) {
            for (INotificationRecipient recipient : app.getRecipients()) {
                recipient.getApps().add((App) app);
                em.merge(recipient);
            }
        } else {
            em.merge(app);
        }
        em.flush();
        return app;
    }

    @Override
    public void updateLastAppTest(String appId, String appTestId) {
        IApp app = getById(appId);
        app.setLastTestId(appTestId);
        em.flush();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.asu.diging.monitor.core.db.impl.IAppDbConnection#delete(edu.asu.diging.
     * monitor.core.model.IApp)
     */
    @Override
    public void delete(IApp element) {
        for (INotificationRecipient recipient : element.getRecipients()) {
            recipient.getApps().remove(element);
        }
        em.remove(element);
        em.flush();
    }

    @Override
    public void deleteRecipientsForApp(IApp element) {
        for (INotificationRecipient recipient : element.getRecipients()) {
            recipient.getApps().remove(element);
            em.merge(recipient);
        }
        em.flush();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.asu.diging.monitor.core.db.impl.IAppDbConnection#getAllRegisteredApps()
     */
    @Override
    public IApp[] getAllRegisteredApps() {
        TypedQuery<IApp> query = em.createQuery("SELECT a FROM App a", IApp.class);
        List<IApp> results = query.getResultList();
        if (results == null) {
            return new IApp[0];
        }
        return results.toArray(new IApp[results.size()]);
    }

    @Override
    public Group[] getAllGroups() {
        TypedQuery<Group> query = em.createQuery("SELECT a FROM Group a", Group.class);
        List<Group> results = query.getResultList();
        if (results == null) {
            return new Group[0];
        }
        return results.toArray(new Group[results.size()]);
    }

    @Override
    public String generateGroupId() {
        String id = null;
        while (true) {
            id = "GRP" + generateUniqueId();
            Object existingFile = getById(id);
            if (existingFile == null) {
                break;
            }
        }
        return id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.diging.monitor.core.db.impl.IAppDbConnection#generateId()
     */
    @Override
    public String generateId() {
        String id = null;
        while (true) {
            id = "APP" + generateUniqueId();
            Object existingFile = getById(id);
            if (existingFile == null) {
                break;
            }
        }
        return id;
    }

    /**
     * This methods generates a new 6 character long id. Note that this method does
     * not assure that the id isn't in use yet.
     * 
     * Adapted from
     * http://stackoverflow.com/questions/9543715/generating-human-readable
     * -usable-short-but-unique-ids
     * 
     * @return 12 character id
     */
    protected String generateUniqueId() {
        char[] chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            builder.append(chars[random.nextInt(62)]);
        }

        return builder.toString();
    }

}
