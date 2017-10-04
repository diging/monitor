package edu.asu.diging.monitor.core.service;

import java.util.List;

import edu.asu.diging.monitor.core.model.IApp;
import edu.asu.diging.monitor.core.model.IAppTest;

public interface IAppManager {

	void addApp(IApp app);

	List<IApp> getApps();

	IAppTest getLatestAppTest(IApp app);

	void addAppTest(IAppTest test, boolean updateAppLastTest);

}