package edu.asu.diging.monitor.core.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.diging.monitor.core.db.IAppDbConnection;
import edu.asu.diging.monitor.core.db.IAppTestDbConnection;
import edu.asu.diging.monitor.core.exceptions.GroupNotFoundException;
import edu.asu.diging.monitor.core.exceptions.UnstorableObjectException;
import edu.asu.diging.monitor.core.model.IApp;
import edu.asu.diging.monitor.core.model.IAppTest;
import edu.asu.diging.monitor.core.model.impl.App;
import edu.asu.diging.monitor.core.service.IAppHelper;
import edu.asu.diging.monitor.core.service.IAppManager;
import edu.asu.diging.monitor.core.service.IPasswordEncryptor;
import edu.asu.diging.monitor.web.admin.forms.AppForm;

@Service
@Transactional
public class AppManager implements IAppManager {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IAppDbConnection dbConnection;

    @Autowired
    private IAppTestDbConnection appTestDbConnection;

    @Autowired
    private IPasswordEncryptor passwordEncryptor;

    @Autowired
    private IAppHelper appHelper;

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.asu.diging.monitor.core.service.impl.IAppManager#addApp(edu.asu.diging.
     * monitor.core.model.IApp)
     */
    @Override
    public IApp addApp(AppForm appForm) throws UnstorableObjectException, GroupNotFoundException {
        IApp app = new App();
        app.setId(dbConnection.generateId());
        appHelper.copyAppInfo(app, appForm);
        encryptPassword(appForm, app);
        try {
            dbConnection.store(app);
        } catch (UnstorableObjectException e) {
            // should never happen, we're setting the id
            logger.error("Could not store app.", e);
        }
        return app;
    }

    @Override
    public IApp updateApp(IApp app, AppForm appForm) throws UnstorableObjectException, GroupNotFoundException {
        deleteExistingRecipients(app);
        appHelper.copyAppInfo(app, appForm);
        return dbConnection.update(app);
    }

    @Override
    public List<IApp> getApps() {
        IApp[] apps = dbConnection.getAllRegisteredApps();
        if (apps != null) {
            List<IApp> appList = Arrays.asList(apps);
            for (IApp app : appList) {
                if (app.getLastTestId() != null) {
                    app.setLastAppTest(appTestDbConnection.getById(app.getLastTestId()));
                }
            }
            return appList;
        }
        return new ArrayList<>();
    }

    @Override
    public IApp getApp(String id) {
        return dbConnection.getById(id);
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

    @Override
    public void deleteApp(String id) {
        IApp app = dbConnection.getById(id);
        if (app != null) {
            appTestDbConnection.deleteTestsForApp(id);
            dbConnection.delete(app);
        }
    }

    protected void deleteExistingRecipients(IApp app) {
        if (app.getRecipients() != null && !app.getRecipients().isEmpty()) {
            dbConnection.deleteRecipientsForApp(app);
        }
    }
    protected void encryptPassword(AppForm appForm, IApp app) {
        if (!appForm.getUsername().isEmpty() && !appForm.getPassword().isEmpty()) {
            app.setUsername(appForm.getUsername());
            app.setPassword(passwordEncryptor.encrypt(appForm.getPassword()));
        } else {
            app.setUsername(null);
            app.setPassword(null);
        }

    }

    @Override
    public IApp updateAppAuth(AppForm appForm, IApp app) {
        encryptPassword(appForm, app);
        dbConnection.updateAppAuth(app);
        return app;
    }
}
