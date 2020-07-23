package edu.asu.diging.monitor.core.service.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import edu.asu.diging.monitor.core.db.INotificationRecipientDbConnection;
import edu.asu.diging.monitor.core.model.INotificationRecipient;
import edu.asu.diging.monitor.core.model.impl.NotificationRecipient;
import edu.asu.diging.monitor.core.notify.impl.EmailNotificationManager;

public class NotificationManagerTest {
	
	@Mock
	private INotificationRecipientDbConnection dbConn;
	
	@Mock
	private EmailNotificationManager emailManager;
	
	@InjectMocks
	private NotificationManager managerToTest;
	
	
	private INotificationRecipient[] storedRecipients;
	private NotificationRecipient recipient1;
	private NotificationRecipient recipient2;
	private String ID1 = "email@ID1";
	private String ID2 = "email@ID2";
	
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		recipient1 = new NotificationRecipient();
		recipient1.setEmail(ID1);
		recipient2 = new NotificationRecipient();
		recipient2.setEmail(ID2);
		storedRecipients = new NotificationRecipient[] {recipient1, recipient2};
		
		Mockito.when(dbConn.getAllRecipients()).thenReturn(storedRecipients);
		Mockito.when(dbConn.getById(ID1)).thenReturn(recipient1);
		Mockito.when(dbConn.getById(ID2)).thenReturn(recipient2);
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
	

}
