package edu.asu.diging.monitor.core.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.diging.monitor.core.db.IAppDbConnection;
import edu.asu.diging.monitor.core.db.IAppTestDbConnection;
import edu.asu.diging.monitor.core.exceptions.UnstorableObjectException;
import edu.asu.diging.monitor.core.model.IApp;
import edu.asu.diging.monitor.core.model.IAppTest;
import edu.asu.diging.monitor.core.service.IAppManager;

@Service
public class AppManager implements IAppManager {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IAppDbConnection dbConnection;
	
	@Autowired
	private IAppTestDbConnection appTestDbConnection;

	/* (non-Javadoc)
	 * @see edu.asu.diging.monitor.core.service.impl.IAppManager#addApp(edu.asu.diging.monitor.core.model.IApp)
	 */
	@Override
	public void addApp(IApp app) {
		app.setId(dbConnection.generateId());
		try {
			dbConnection.store(app);
		} catch (UnstorableObjectException e) {
			// should never happen, we're setting the id
			logger.error("Could not store app.", e);
		}
	}
	
	@Override
	public List<IApp> getApps() {
		IApp[] apps = dbConnection.getAllRegisteredApps();
		if (apps != null) {
			List<IApp> appList = Arrays.asList(apps);
			for (IApp app : appList) {
				if (app.getLastTestId() != null ) {
					app.setLastAppTest(appTestDbConnection.getById(app.getLastTestId()));
				}
			}
			return appList;
		}
		return new ArrayList<>();
	}
	
	@Override
	public IAppTest getLatestAppTest(IApp app) {
		if (app.getLastTestId() == null) {
			return null;
		}
		return appTestDbConnection.getById(app.getLastTestId());
	}
	
	@Override
	public void addAppTest(IAppTest test, boolean updateAppLastTest) {
		test.setId(appTestDbConnection.generateId());
		try {
			appTestDbConnection.store(test);
		} catch (UnstorableObjectException e) {
			// should never happen
			logger.error("Could not store app test.", e);
		}
		
		if (updateAppLastTest) {
			dbConnection.updateLastAppTest(test.getAppId(), test.getId());
		}
	}
}
