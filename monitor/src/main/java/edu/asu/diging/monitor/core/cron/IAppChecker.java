package edu.asu.diging.monitor.core.cron;

import edu.asu.diging.monitor.core.model.IApp;

public interface IAppChecker {

	void checkApp(IApp app);
	
}