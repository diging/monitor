package edu.asu.diging.monitor.core.service.impl;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.diging.monitor.core.db.INotificationRecipientDbConnection;
import edu.asu.diging.monitor.core.exceptions.EmailAlreadyRegisteredException;
import edu.asu.diging.monitor.core.exceptions.UnstorableObjectException;
import edu.asu.diging.monitor.core.model.AppStatus;
import edu.asu.diging.monitor.core.model.IApp;
import edu.asu.diging.monitor.core.model.INotificationRecipient;
import edu.asu.diging.monitor.core.model.impl.App;
import edu.asu.diging.monitor.core.model.impl.NotificationRecipient;
import edu.asu.diging.monitor.core.notify.IEmailNotificationManager;
import edu.asu.diging.monitor.core.service.INotificationManager;

@Service
public class NotificationManager implements INotificationManager {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private INotificationRecipientDbConnection dbConnection;

	@Autowired
	private IEmailNotificationManager emailManager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.asu.diging.monitor.core.service.impl.INotificationManager#addRecipient(
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public boolean addRecipient(String name, String email, List<App> apps) throws EmailAlreadyRegisteredException {
		if (email == null || email.trim().isEmpty()) {
			return false;
		}
		if (dbConnection.getById(email) != null) {
			throw new EmailAlreadyRegisteredException();
		}
		INotificationRecipient recipient = new NotificationRecipient();
		recipient.setName(name);
		recipient.setEmail(email);
		recipient.setApps(apps);
		try {
			dbConnection.store(recipient);
		} catch (UnstorableObjectException e) {
			// should not happen
			logger.error("Could not store recipient.", e);
		}
		return true;
	}

	@Override
	public void deleteRecipient(String email) {
		dbConnection.delete(email);
	}

	@Override
	public List<INotificationRecipient> getAllRecipients() {
		return Arrays.asList(dbConnection.getAllRecipients());
	}

	@Override //change here
	public void sendNotificationEmails(IApp app, AppStatus previousStatus) {
		INotificationRecipient[] recipients = dbConnection.getRecipientsByAppId(app.getId());
		for (INotificationRecipient recipient : Arrays.asList(recipients)) {
			emailManager.sendAppStatusNotificationEmail(recipient.getEmail(), app, previousStatus.toString(),
					app.getLastAppTest().getStatus().toString());
		}
	}
}
