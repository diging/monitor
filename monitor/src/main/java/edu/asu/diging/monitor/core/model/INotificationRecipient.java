package edu.asu.diging.monitor.core.model;

import java.util.List;

import edu.asu.diging.monitor.core.model.impl.App;

public interface INotificationRecipient {

	String getName();

	void setName(String name);

	String getEmail();

	void setEmail(String email);
	
	void setApps(List<App> apps);
	
	List<App> getApps();

}