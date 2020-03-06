package edu.asu.diging.monitor.core.cron;

import org.springframework.scheduling.annotation.Async;

import edu.asu.diging.monitor.core.model.IApp;

public interface IAppChecker {

	void checkApp(IApp app);
	
	void checkAppNow(IApp app);

}