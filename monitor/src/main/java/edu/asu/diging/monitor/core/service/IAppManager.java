package edu.asu.diging.monitor.core.service;

import java.util.List;

import edu.asu.diging.monitor.core.exceptions.GroupNotFoundException;
import edu.asu.diging.monitor.core.exceptions.UnstorableObjectException;
import edu.asu.diging.monitor.core.model.IApp;
import edu.asu.diging.monitor.core.model.IAppTest;
import edu.asu.diging.monitor.web.admin.forms.AppForm;

public interface IAppManager {

    IApp addApp(AppForm appForm) throws UnstorableObjectException, GroupNotFoundException;

    IApp updateApp(IApp app, AppForm appForm) throws UnstorableObjectException, GroupNotFoundException;

    List<IApp> getApps();

    IApp getApp(String id);

    IAppTest getLatestAppTest(IApp app);

    void addAppTest(IAppTest test, boolean updateAppLastTest);

    void deleteApp(String id);

    IApp updateAppAuth(AppForm appForm, IApp app);

}