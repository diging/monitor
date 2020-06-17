package edu.asu.diging.monitor.core.db;

import java.util.List;

import edu.asu.diging.monitor.core.exceptions.UnstorableObjectException;
import edu.asu.diging.monitor.core.model.impl.Group;

public interface IGroupDbConnection {

    Group createGroup(Group group) throws UnstorableObjectException;

    Group getGroupById(String id);

    String generateGroupId();

    List<Group> getAllGroups();

    void update(Group group);
    
    void deleteGroup(Group group);

    void deleteGroupForApps(Group group);

}
