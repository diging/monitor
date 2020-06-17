package edu.asu.diging.monitor.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.diging.monitor.core.db.IGroupDbConnection;
import edu.asu.diging.monitor.core.exceptions.GroupNotFoundException;
import edu.asu.diging.monitor.core.exceptions.UnstorableObjectException;
import edu.asu.diging.monitor.core.model.impl.App;
import edu.asu.diging.monitor.core.model.impl.Group;
import edu.asu.diging.monitor.core.service.IAppManager;
import edu.asu.diging.monitor.core.service.IGroupManager;
import edu.asu.diging.monitor.web.admin.forms.GroupForm;

@Service
@Transactional
public class GroupManager implements IGroupManager {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IGroupDbConnection dbConnection;

    @Autowired
    private IAppManager appManager;

    @Override
    public Group createGroup(String name) throws UnstorableObjectException {
        Group group = new Group();
        group.setName(name);
        group.setId(dbConnection.generateGroupId());
        dbConnection.createGroup(group);
        return group;
    }

    @Override
    public Group getGroup(String id) throws GroupNotFoundException {
        Group group = dbConnection.getGroupById(id);
        if (group == null) {
            logger.error("No group exists for this id");
            throw new GroupNotFoundException("No group exists for this id");
        }
        return group;
    }

    @Override
    public List<Group> getGroups() {
        return dbConnection.getAllGroups();
    }

    @Override
    public void updateGroup(Group group, GroupForm groupForm) {
        group.setName(groupForm.getName());
        if (groupForm.getAppIds() != null) {
            group.setApps(
                    groupForm.getAppIds().stream().map(id -> (App) appManager.getApp(id)).collect(Collectors.toList()));
        } else {
            group.setApps(new ArrayList<App>());
        }
        dbConnection.update(group);
    }

    public void deleteGroup(Group group) {
        dbConnection.deleteGroup(group);
    }

    @Override
    public void deleteExistingApps(Group group) {
        dbConnection.deleteGroupForApps(group);
    }
}
