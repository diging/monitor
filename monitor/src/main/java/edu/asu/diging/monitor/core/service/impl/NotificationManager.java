package edu.asu.diging.monitor.core.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
	
	/* (non-Javadoc)
	 * @see edu.asu.diging.monitor.core.service.impl.INotificationManager#addRecipient(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean addRecipient(String name, String email) throws EmailAlreadyRegisteredException {
		if (email == null || email.trim().isEmpty()) {
			return false;
		}
		if (dbConnection.getById(email) != null) {
			throw new EmailAlreadyRegisteredException();
		}
		INotificationRecipient recipient = new NotificationRecipient();
		recipient.setName(name);
		recipient.setEmail(email);
		try {
			dbConnection.store(recipient);
		} catch (UnstorableObjectException e) {
			// should not happen
			logger.error("Could not store recipient.", e);
		}
		return true;
	}
	
	@Override
	public Map<String,String> showRecipients(){
		INotificationRecipient[] recipients = dbConnection.getAllRecipients();
		Map<String,String> recipientInfo = new HashMap<>();
		Arrays.asList(recipients).forEach(x -> recipientInfo.put(x.getName(),x.getEmail()));
		return recipientInfo;
	}
	
	@Override
	public void sendNotificationEmails(IApp app, AppStatus previousStatus) {
		INotificationRecipient[] recipients = dbConnection.getAllRecipients();
		for (INotificationRecipient recipient : Arrays.asList(recipients)) {
			emailManager.sendAppStatusNotificationEmail(recipient.getEmail(), app, previousStatus.toString(), app.getLastAppTest().getStatus().toString());
		}
	}
}
