package edu.asu.diging.monitor.core.db;


import edu.asu.diging.monitor.core.exceptions.UnstorableObjectException;
import edu.asu.diging.monitor.core.model.IApp;

public interface IAppDbConnection {

    IApp getById(String id);

    IApp store(IApp app) throws UnstorableObjectException;

    IApp update(IApp app);

    void delete(IApp element);

    IApp[] getAllRegisteredApps();

    String generateId();

    void updateLastAppTest(String appId, String appTestId);

    void deleteRecipientsForApp(IApp app);

    void updateAppAuth(IApp app);

    

}