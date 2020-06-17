package edu.asu.diging.monitor.core.service;

import java.util.List;

import edu.asu.diging.monitor.core.model.IApp;
import edu.asu.diging.monitor.core.model.IAppTest;
import edu.asu.diging.monitor.web.admin.forms.AppForm;

public interface IAppManager {

	void addApp(IApp app,  AppForm appForm);
	
	void updateApp(IApp app,  AppForm appForm);

	List<IApp> getApps();
	
	IApp getApp(String id);

	IAppTest getLatestAppTest(IApp app);

	void addAppTest(IAppTest test, boolean updateAppLastTest);

	void deleteApp(String id);
	
	void deleteExistingRecipients(IApp app);
	
}