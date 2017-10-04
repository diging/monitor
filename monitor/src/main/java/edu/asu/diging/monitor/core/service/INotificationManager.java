package edu.asu.diging.monitor.core.service;

import edu.asu.diging.monitor.core.exceptions.EmailAlreadyRegisteredException;
import edu.asu.diging.monitor.core.model.AppStatus;
import edu.asu.diging.monitor.core.model.IApp;

public interface INotificationManager {

	boolean addRecipient(String name, String email) throws EmailAlreadyRegisteredException;

	void sendNotificationEmails(IApp app, AppStatus previousStatus);

}