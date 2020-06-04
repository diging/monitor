package edu.asu.diging.monitor.core.service.impl;

import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import edu.asu.diging.monitor.core.db.impl.GroupDbConnection;
import edu.asu.diging.monitor.core.exceptions.GroupNotFoundException;
import edu.asu.diging.monitor.core.exceptions.UnstorableObjectException;
import edu.asu.diging.monitor.core.model.impl.Group;

public class GroupManagerTest {

    @Mock
    private GroupDbConnection dbConnection;

    @InjectMocks
    private GroupManager managerToTest;

    private List<Group> storedGroups;
    private Group group1;
    private Group group2;
    private Group group3;
    private String ID1 = "GROUP1";
    private String ID2 = "GROUP2";
    private String ID3 = "GROUP3";

    @Before
    public void setUp() throws GroupNotFoundException {
        MockitoAnnotations.initMocks(this);
        group1 = new Group();
        group1.setId(ID1);
        group2 = new Group();
        group2.setId(ID2);
        group3 = new Group();
        group3.setId(null);
        storedGroups = new ArrayList<Group>();
        storedGroups.add(group1);
        storedGroups.add(group2);

        Mockito.when(dbConnection.getAllGroups()).thenReturn(storedGroups);
        Mockito.when(dbConnection.getGroupById(ID1)).thenReturn(group1);
        Mockito.when(dbConnection.getGroupById(ID2)).thenReturn(group2);
        Mockito.when(dbConnection.getGroupById(ID3)).thenReturn(group3);
    }

    @Test
    public void test_getGroups_success() {
        List<Group> results = managerToTest.getGroups();
        Assert.assertArrayEquals(storedGroups.toArray(), results.toArray());
    }

    @Test
    public void test_getGroups_noGroups() {
        Mockito.when(dbConnection.getAllGroups()).thenReturn(new ArrayList<Group>());
        Assert.assertTrue(managerToTest.getGroups().isEmpty());
    }

    @Test
    public void test_createGroup_success() throws UnstorableObjectException, GroupNotFoundException {
        String id = "GROUP1";
        Mockito.when(dbConnection.generateGroupId()).thenReturn(id);
        Group group = managerToTest.createGroup(id);
        Mockito.verify(dbConnection).createGroup(group);
        Assert.assertEquals(id, group.getId());
    }

    @Test
    public void test_createGroup_failure() throws UnstorableObjectException {
        Mockito.when(dbConnection.generateGroupId()).thenReturn(null);
        Mockito.when(dbConnection.createGroup(group3))
                .thenThrow(new UnstorableObjectException("No group with this Id"));
        assertThrows(UnstorableObjectException.class, () -> dbConnection.createGroup(group3));

    }

}
