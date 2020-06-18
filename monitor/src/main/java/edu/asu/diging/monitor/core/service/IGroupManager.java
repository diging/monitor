package edu.asu.diging.monitor.core.service;

import java.util.List;

import edu.asu.diging.monitor.core.exceptions.GroupNotFoundException;
import edu.asu.diging.monitor.core.exceptions.UnstorableObjectException;
import edu.asu.diging.monitor.core.model.impl.Group;
import edu.asu.diging.monitor.web.admin.forms.GroupForm;

public interface IGroupManager {

    List<Group> getGroups();

    Group getGroup(String id) throws GroupNotFoundException;

    Group createGroup(String name) throws UnstorableObjectException;

    void updateGroup(Group group, GroupForm groupForm);

    void deleteGroup(Group group);

    void deleteGroupForApps(Group group);

    void addAppsToGroup(Group group, GroupForm groupForm);
}
