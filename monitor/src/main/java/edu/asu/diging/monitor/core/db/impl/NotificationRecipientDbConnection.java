package edu.asu.diging.monitor.core.db.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import edu.asu.diging.monitor.core.db.INotificationRecipientDbConnection;
import edu.asu.diging.monitor.core.exceptions.UnstorableObjectException;
import edu.asu.diging.monitor.core.model.IApp;
import edu.asu.diging.monitor.core.model.INotificationRecipient;
import edu.asu.diging.monitor.core.model.impl.NotificationRecipient;

@Component
@Transactional
public class NotificationRecipientDbConnection implements INotificationRecipientDbConnection {

	@PersistenceContext
    private EntityManager em;
	
	/* (non-Javadoc)
	 * @see edu.asu.diging.monitor.core.db.impl.INotificationRecipientDbConnection#getById(java.lang.String)
	 */
	@Override
	public INotificationRecipient getById(String id) {
		return em.find(NotificationRecipient.class, id);
	}
	
	/* (non-Javadoc)
	 * @see edu.asu.diging.monitor.core.db.impl.INotificationRecipientDbConnection#store(edu.asu.diging.monitor.core.model.INotificationRecipient)
	 */
	@Override
	public INotificationRecipient store(INotificationRecipient recipient) throws UnstorableObjectException {
        if (recipient.getEmail() == null) {
            throw new UnstorableObjectException("Recipient doesn't have an email address.");
        }
        em.persist(recipient);
        em.flush();
        return recipient;
    }
	
	@Override
	public INotificationRecipient[] getAllRecipients() {
        TypedQuery<INotificationRecipient> query = em.createQuery("SELECT n FROM NotificationRecipient n", INotificationRecipient.class);
        List<INotificationRecipient> results = query.getResultList();
        if (results == null) {
            return new INotificationRecipient[0];
        }
        return results.toArray(new INotificationRecipient[results.size()]);
    }
	
	/* (non-Javadoc)
	 * @see edu.asu.diging.monitor.core.db.impl.INotificationRecipientDbConnection#delete(java.lang.String)
	 */
	@Override
	public void delete(String email) {
		INotificationRecipient recipient = getById(email);
		if (recipient != null) {
			em.remove(recipient);
		}
	}

}
