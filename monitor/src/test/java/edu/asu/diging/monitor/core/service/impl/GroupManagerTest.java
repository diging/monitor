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

import edu.asu.diging.monitor.core.db.IAppDbConnection;
import edu.asu.diging.monitor.core.exceptions.GroupNotFoundException;
import edu.asu.diging.monitor.core.exceptions.UnstorableObjectException;
import edu.asu.diging.monitor.core.model.impl.Group;

public class GroupManagerTest {

    @Mock
    private IAppDbConnection dbConnection;

    @InjectMocks
    private GroupManager managerToTest;
    
    private List<Group> storedGroups;
    private Group group1;
    private Group group2;
    private String ID1 = "GROUP1";
    private String ID2 = "GROUP2";

    @Before
    public void setUp() throws GroupNotFoundException {
        MockitoAnnotations.initMocks(this);
        group1 = new Group();
        group1.setId(ID1);
        group2 = new Group();
        group2.setId(ID2);
        storedGroups = new ArrayList<Group>();
        storedGroups.add(group1);
        storedGroups.add(group2);

        Mockito.when(dbConnection.getAllGroups()).thenReturn(storedGroups);
        Mockito.when(dbConnection.getGroupById(ID1)).thenReturn(group1);
        Mockito.when(dbConnection.getGroupById(ID2)).thenReturn(group2);
    }

    @Test
    public void test_createGroup_success() throws UnstorableObjectException {
        String id = "GROUP1";
        Mockito.when(dbConnection.generateGroupId()).thenReturn(id);
        Group group =  managerToTest.createGroup(id);
        Mockito.verify(dbConnection).createGroup(group);
        Assert.assertEquals(id, group.getId());
    }
    
    @Test
    public void test_getGroups_success() {
        List<Group> results = managerToTest.getGroups();
        Assert.assertArrayEquals(storedGroups.toArray(), results.toArray());
    }
    

}
