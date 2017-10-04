package edu.asu.diging.monitor.core.cron;

import java.time.OffsetDateTime;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import edu.asu.diging.monitor.core.model.IApp;
import edu.asu.diging.monitor.core.model.IAppTest;
import edu.asu.diging.monitor.core.service.IAppManager;

@Component
@PropertySource(value="classpath:/config.properties")
public class TestAppsCronJob {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${ping.default.interval}")
	private String defaultPingInterval;
	
	@Autowired
	private IAppManager appManager;
	
	@Autowired
	private IAppChecker appChecker;

	@Scheduled(fixedRateString="${schedule.ping.check}")
	public void execAppChecks() {
		logger.debug("Checking if it's time for app tests.");
		List<IApp> apps = appManager.getApps();
		for (IApp app : apps) {
			// if test has never been run, run it now
			if (app.getLastTestId() == null) {
				appChecker.checkApp(app);
				continue;
			}
			
			IAppTest latestTest = appManager.getLatestAppTest(app);
			int pingInterval = app.getPingInterval() > 0 ? app.getPingInterval() : new Integer(defaultPingInterval.trim());
			// if it is time to ping again
			if ((OffsetDateTime.now().toEpochSecond() - latestTest.getPingTime().toEpochSecond()) > pingInterval) {
				appChecker.checkApp(app);
			}
		}
	}
}
