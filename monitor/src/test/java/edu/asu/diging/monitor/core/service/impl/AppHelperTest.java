package edu.asu.diging.monitor.core.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.asu.diging.monitor.core.exceptions.GroupNotFoundException;
import edu.asu.diging.monitor.core.exceptions.UnstorableObjectException;
import edu.asu.diging.monitor.core.model.impl.App;
import edu.asu.diging.monitor.core.service.IGroupManager;
import edu.asu.diging.monitor.core.service.INotificationManager;
import edu.asu.diging.monitor.web.admin.forms.AppForm;

public class AppHelperTest {

    @Mock
    private IGroupManager groupManager;

    @Mock
    private INotificationManager manager;

    private App app1;

    @InjectMocks
    private AppHelper helperToTest;

    @Before
    public void setUp() throws UnstorableObjectException {
        MockitoAnnotations.initMocks(this);
        app1 = new App();

    }

    @Test
    public void test_copyAppInfo_success() throws UnstorableObjectException, GroupNotFoundException {
        AppForm appForm = new AppForm();
        appForm.setName("APP1");
        appForm.setDescription("Desc");
        helperToTest.copyAppInfo(app1, appForm);
        Assert.assertEquals(appForm.getName(), app1.getName());
        Assert.assertEquals(appForm.getDescription(), app1.getDescription());
    }

    @Test
    public void test_copyAppInfoToForm_success() {
        App app = new App();
        app.setName("APP1");
        app.setDescription("Desc");
        AppForm appForm = new AppForm();
        helperToTest.copyAppInfoToForm(app, appForm);
        Assert.assertEquals(appForm.getName(), app.getName());
        Assert.assertEquals(appForm.getDescription(), app.getDescription());
    }
}
