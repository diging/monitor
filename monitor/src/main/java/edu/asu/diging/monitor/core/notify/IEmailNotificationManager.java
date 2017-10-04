package edu.asu.diging.monitor.core.notify;

import edu.asu.diging.monitor.core.model.IApp;

public interface IEmailNotificationManager {

	void sendAppStatusNotificationEmail(String email, IApp app, String prevStatus, String newStatus);

}