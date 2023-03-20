package edu.asu.diging.monitor.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import edu.asu.diging.monitor.core.db.INotificationRecipientDbConnection;
import edu.asu.diging.monitor.core.exceptions.NoEmailRecipientException;
import edu.asu.diging.monitor.core.exceptions.UnstorableObjectException;
import edu.asu.diging.monitor.core.model.INotificationRecipient;
import edu.asu.diging.monitor.core.model.impl.App;
import edu.asu.diging.monitor.core.model.impl.NotificationRecipient;
import edu.asu.diging.monitor.core.notify.impl.EmailNotificationManager;
import edu.asu.diging.monitor.core.service.IAppManager;

public class NotificationManagerTest {
	
	@Mock
	private INotificationRecipientDbConnection dbConn;
	
	@Mock
	private EmailNotificationManager emailManager;
	
	@Mock
	private IAppManager appmanager;
	
	@InjectMocks
	private NotificationManager managerToTest;
	
	
	private INotificationRecipient[] storedRecipients;
	private NotificationRecipient recipient1;
	private NotificationRecipient recipient2;
	private String ID1 = "email@ID1";
	private String ID2 = "email@ID2";
    private String id1Name = "ID1";
    private String id2Name = "ID2";
    private String appId1 = "App1";
    private String appId2 = "App2";
    private App app1 = new App();
    private App app2 = new App();
    List<String> appIds = new ArrayList<>();
	
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		recipient1 = new NotificationRecipient();
		recipient1.setEmail(ID1);
        recipient1.setName(id1Name);
		recipient2 = new NotificationRecipient();
		recipient2.setEmail(ID2);
        recipient2.setName(id2Name);
		storedRecipients = new NotificationRecipient[] {recipient1, recipient2};
		
        app1.setId(appId1);
        app1.setName(appId1);
        app2.setId(appId2);
        app2.setName(appId2);

        appIds.add(appId1);
        appIds.add(appId2);
		
		Mockito.when(dbConn.getAllRecipients()).thenReturn(storedRecipients);
		Mockito.when(dbConn.getById(ID1)).thenReturn(recipient1);
		Mockito.when(dbConn.getById(ID2)).thenReturn(recipient2);
        Mockito.when(appmanager.getApp(appId1)).thenReturn(app1);
        Mockito.when(appmanager.getApp(appId2)).thenReturn(app2);
	}
	
	
	
	@Test
	public void test_getRecipients_success() {
		List<INotificationRecipient> results = managerToTest.getAllRecipients();
		Assert.assertArrayEquals(storedRecipients, results.toArray());
	}
	
	
	@Test
    public void test_deleteRecipient_success() {
        managerToTest.deleteRecipient(ID1);
        Mockito.verify(dbConn).delete(ID1);
    }
	
    @Test
    public void test_modifyRecipient_success() throws NoEmailRecipientException {
        managerToTest.modifyRecipient(ID1, id1Name, appIds);
        try {
            Mockito.verify(dbConn).update(Mockito.any());
        } catch (UnstorableObjectException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_modifyRecipient_failure_emailNull() throws NoEmailRecipientException {
        String email = null;
        Assert.assertFalse(managerToTest.modifyRecipient(email, id1Name, appIds));
    }

    @Test(expected = NoEmailRecipientException.class)
    public void test_modifyRecipient_failure_NoEmailRecipient() throws NoEmailRecipientException {
        Mockito.when(dbConn.getById(Mockito.anyString())).thenReturn(null);
        managerToTest.modifyRecipient(ID1, id1Name, appIds);
    }
	

}
