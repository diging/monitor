package edu.asu.diging.monitor.core.service;

import java.util.List;

import edu.asu.diging.monitor.core.exceptions.GroupNotFoundException;
import edu.asu.diging.monitor.core.model.impl.Group;

public interface IGroupManager {

    List<Group> getGroups();

    Group getGroup(String id) throws GroupNotFoundException;

    Group createGroup(String name) throws GroupNotFoundException;

}
