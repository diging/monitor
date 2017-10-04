package edu.asu.diging.monitor.core.db.impl;

import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import edu.asu.diging.monitor.core.db.IAppTestDbConnection;
import edu.asu.diging.monitor.core.exceptions.UnstorableObjectException;
import edu.asu.diging.monitor.core.model.IApp;
import edu.asu.diging.monitor.core.model.IAppTest;
import edu.asu.diging.monitor.core.model.impl.AppTest;

@Component
@Transactional
public class AppTestDbConnection implements IAppTestDbConnection {

	@PersistenceContext
    private EntityManager em;
	
	/* (non-Javadoc)
	 * @see edu.asu.diging.monitor.core.db.impl.IAppTestDbConnection#getById(java.lang.String)
	 */
	@Override
	public IAppTest getById(String id) {
		return em.find(AppTest.class, id);
	}
	
	/* (non-Javadoc)
	 * @see edu.asu.diging.monitor.core.db.impl.IAppTestDbConnection#storeApp(edu.asu.diging.monitor.core.model.IAppTest)
	 */
	@Override
	public IAppTest store(IAppTest appTest) throws UnstorableObjectException {
        if (appTest.getId() == null) {
            throw new UnstorableObjectException("AppTest does not have an id.");
        }
        em.persist(appTest);
        em.flush();
        return appTest;
    }
	
	@Override
	public String generateId() {
        String id = null;
        while (true) {
            id = "TEST" + generateUniqueId();
            Object existingFile = getById(id);
            if (existingFile == null) {
                break;
            }
        }
        return id;
    }
	
	@Override
	public void deleteTestsForApp(String appId) {
		TypedQuery<IAppTest> query = em.createQuery("SELECT a FROM AppTest a WHERE a.appId = :appId", IAppTest.class);
        query.setParameter("appId", appId);
		List<IAppTest> results = query.getResultList();
		
		if (results != null) {
			for(IAppTest test : results) {
				em.remove(test);
			}
		}
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
