package edu.asu.diging.monitor.core.service.impl;

import static org.mockito.Mockito.never;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import edu.asu.diging.monitor.core.db.IAppDbConnection;
import edu.asu.diging.monitor.core.db.IAppTestDbConnection;
import edu.asu.diging.monitor.core.exceptions.GroupNotFoundException;
import edu.asu.diging.monitor.core.exceptions.UnstorableObjectException;
import edu.asu.diging.monitor.core.model.IApp;
import edu.asu.diging.monitor.core.model.IAppTest;
import edu.asu.diging.monitor.core.model.impl.App;
import edu.asu.diging.monitor.core.model.impl.AppTest;
import edu.asu.diging.monitor.core.service.IAppHelper;
import edu.asu.diging.monitor.web.admin.forms.AppForm;

public class AppManagerTest {

    @Mock
    private IAppDbConnection dbConnection;
    
    @Mock
    private IAppTestDbConnection appTestDbConnection;
    
    @Mock
    private IAppHelper appHelper;

    @InjectMocks
    private AppManager managerToTest;
    
    private IApp[] storedApps;
    private App app1;
    private App app2;
    private String ID1 = "ID1";
    private String ID2 = "ID2";
    private AppTest test1;
    private String TEST1 = "TEST1";
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        
        app1 = new App();
        app1.setId(ID1);
        app2 = new App();
        app2.setId(ID2);
        storedApps = new App[] { app1, app2 };
        
        Mockito.when(dbConnection.getAllRegisteredApps()).thenReturn(storedApps);
        Mockito.when(dbConnection.getById(ID1)).thenReturn(app1);
        
        test1 = new AppTest();
        test1.setAppId(ID1);
        test1.setId(TEST1);
        app1.setLastTestId(TEST1);
        Mockito.when(appTestDbConnection.getById(TEST1)).thenReturn(test1);
    }
    
    @Test
    public void test_addApp_success() throws UnstorableObjectException, GroupNotFoundException {
        String id = "ID1";
        Mockito.when(dbConnection.generateId()).thenReturn(id);
        IApp app = managerToTest.addApp(new AppForm());
        Mockito.verify(dbConnection).store(app);
        Assert.assertEquals(id, app.getId());
    }
    
    @Test
    public void test_getApps_success() {
        List<IApp> results = managerToTest.getApps();
        Assert.assertArrayEquals(storedApps, results.toArray());
        Assert.assertEquals(test1, app1.getLastAppTest());
    }
    
    @Test
    public void test_getApps_noApps() {
        Mockito.when(dbConnection.getAllRegisteredApps()).thenReturn(new App[0]);
        Assert.assertTrue(managerToTest.getApps().isEmpty());
    }
    
    @Test
    public void test_getLatestAppTest_success() {
        IAppTest test = managerToTest.getLatestAppTest(app1);
        Assert.assertEquals(test1, test);
    }
    
    @Test
    public void test_getLatestAppTest_noTest() {
        IAppTest test = managerToTest.getLatestAppTest(app2);
        Assert.assertNull(test);
    }
    
    @Test
    public void test_addAppTest_updateApp() throws UnstorableObjectException {
        String newTestId = "NEWID";
        Mockito.when(appTestDbConnection.generateId()).thenReturn(newTestId);
        AppTest test = new AppTest();
        String appId = "APPID";
        test.setAppId(appId);
        managerToTest.addAppTest(test, true);
        Mockito.verify(appTestDbConnection).store(test);
        Mockito.verify(dbConnection).updateLastAppTest(appId, newTestId);
    }
    
    @Test
    public void test_addAppTest_doNotUpdateApp() throws UnstorableObjectException {
        String newTestId = "NEWID";
        Mockito.when(appTestDbConnection.generateId()).thenReturn(newTestId);
        AppTest test = new AppTest();
        String appId = "APPID";
        test.setAppId(appId);
        managerToTest.addAppTest(test, false);
        Mockito.verify(appTestDbConnection).store(test);
        Mockito.verify(dbConnection, never()).updateLastAppTest(appId, newTestId);
    }
    
    @Test
    public void test_deleteApp_success() {
        managerToTest.deleteApp(ID1);
        Mockito.verify(appTestDbConnection).deleteTestsForApp(ID1);
        Mockito.verify(dbConnection).delete(app1);
    }
}
