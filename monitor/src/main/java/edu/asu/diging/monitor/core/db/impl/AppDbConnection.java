package edu.asu.diging.monitor.core.db.impl;

import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import edu.asu.diging.monitor.core.db.IAppDbConnection;
import edu.asu.diging.monitor.core.exceptions.UnstorableObjectException;
import edu.asu.diging.monitor.core.model.IApp;
import edu.asu.diging.monitor.core.model.INotificationRecipient;
import edu.asu.diging.monitor.core.model.impl.App;

@Component
@Transactional
public class AppDbConnection implements IAppDbConnection {

	@PersistenceContext
    private EntityManager em;
	
	/* (non-Javadoc)
	 * @see edu.asu.diging.monitor.core.db.impl.IAppDbConnection#getAppById(java.lang.String)
	 */
	@Override
	public IApp getById(String id) {
        return em.find(App.class, id);
    }
	
	/* (non-Javadoc)
	 * @see edu.asu.diging.monitor.core.db.impl.IAppDbConnection#storeModifiedApp(edu.asu.diging.monitor.core.model.IApp)
	 */
	@Override
	public IApp store(IApp app) throws UnstorableObjectException {
        if (app.getId() == null) {
            throw new UnstorableObjectException("App does not have an id.");
        }
        em.persist(app);
        em.flush();
        return app;
    }
	
	@Override
	public IApp update(IApp app) {
		em.merge(app);
		return app;
	}
	
	@Override
	public void updateLastAppTest(String appId, String appTestId) {
		IApp app = getById(appId);
		app.setLastTestId(appTestId);
		em.flush();
	}
	
	/* (non-Javadoc)
	 * @see edu.asu.diging.monitor.core.db.impl.IAppDbConnection#delete(edu.asu.diging.monitor.core.model.IApp)
	 */
    @Override
    public void delete(IApp element) {
        for (INotificationRecipient recipient : element.getRecipients()) {
            recipient.getApps().remove(element);
        }
        em.remove(element);
    }

	/* (non-Javadoc)
	 * @see edu.asu.diging.monitor.core.db.impl.IAppDbConnection#getAllRegisteredApps()
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
	
	/* (non-Javadoc)
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
     * This methods generates a new 6 character long id. Note that this method
     * does not assure that the id isn't in use yet.
     * 
     * Adapted from
     * http://stackoverflow.com/questions/9543715/generating-human-readable
     * -usable-short-but-unique-ids
     * 
     * @return 12 character id
     */
    protected String generateUniqueId() {
        char[] chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
                .toCharArray();

        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            builder.append(chars[random.nextInt(62)]);
        }

        return builder.toString();
    }
}

