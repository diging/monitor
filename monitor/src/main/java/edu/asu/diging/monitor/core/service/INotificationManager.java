package edu.asu.diging.monitor.core.service;


import java.util.List;
import edu.asu.diging.monitor.core.exceptions.EmailAlreadyRegisteredException;
import edu.asu.diging.monitor.core.model.AppStatus;
import edu.asu.diging.monitor.core.model.IApp;
import edu.asu.diging.monitor.core.model.INotificationRecipient;

public interface INotificationManager {

	boolean addRecipient(String name, String email, List<String> apps) throws EmailAlreadyRegisteredException;
	
	boolean modifyRecipient(String email);

	void sendNotificationEmails(IApp app, AppStatus previousStatus);
	
	List<INotificationRecipient> getAllRecipients();
	
	void deleteRecipient(String email);
	
	INotificationRecipient getRecipient(String id);

}