package edu.asu.diging.monitor.web.admin.forms;

import java.util.List;

import edu.asu.diging.monitor.core.model.IApp;

public class RecipientForm {

	private String name;
	private String email;
	private List<IApp> apps;
	
	public List<IApp> getApps() {
		return apps;
	}
	public void setApps(List<IApp> apps) {
		this.apps = apps;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
