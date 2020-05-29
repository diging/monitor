package edu.asu.diging.monitor.core.db;

import java.util.List;

import edu.asu.diging.monitor.core.exceptions.GroupNotFoundException;
import edu.asu.diging.monitor.core.exceptions.UnstorableObjectException;
import edu.asu.diging.monitor.core.model.impl.Group;

public interface IGroupDbConnection {

    Group createGroup(Group group) throws UnstorableObjectException;

    Group getGroupById(String id) throws GroupNotFoundException;

    String generateGroupId();

    List<Group> getAllGroups();

}
