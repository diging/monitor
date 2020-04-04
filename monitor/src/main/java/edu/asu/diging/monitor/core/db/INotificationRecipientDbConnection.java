package edu.asu.diging.monitor.core.db;

import edu.asu.diging.monitor.core.exceptions.UnstorableObjectException;
import edu.asu.diging.monitor.core.model.INotificationRecipient;

public interface INotificationRecipientDbConnection {

	INotificationRecipient getById(String id);

	INotificationRecipient store(INotificationRecipient recipient) throws UnstorableObjectException;

	void delete(String email);

	INotificationRecipient[] getAllRecipients();
	
	INotificationRecipient[] getRecipientsByAppId(String appId);

}