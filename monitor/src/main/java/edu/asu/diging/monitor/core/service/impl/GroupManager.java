package edu.asu.diging.monitor.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.diging.monitor.core.db.IAppTestDbConnection;
import edu.asu.diging.monitor.core.db.IGroupDbConnection;
import edu.asu.diging.monitor.core.exceptions.GroupNotFoundException;
import edu.asu.diging.monitor.core.exceptions.UnstorableObjectException;
import edu.asu.diging.monitor.core.model.IApp;
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

    @Autowired
    private IAppTestDbConnection appTestDbConnection;

    @Override
    public Group createGroup(GroupForm groupForm) throws UnstorableObjectException {
        Group group = new Group();
        group.setName(groupForm.getName());
        group.setId(dbConnection.generateGroupId());
        dbConnection.createGroup(group);
        addAppsToGroup(group, groupForm);
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
        List<Group> groupList = dbConnection.getAllGroups();
        if (groupList != null) {
            for (Group group : groupList) {
                for (IApp app : group.getApps()) {
                    if (app.getLastTestId() != null) {
                        app.setLastAppTest(appTestDbConnection.getById(app.getLastTestId()));
                    }
                }

            }
            return groupList;
        }
        return new ArrayList<>();
    }

    @Override
    public void updateGroup(Group group, GroupForm groupForm) {
        deleteGroupForApps(group);
        group.setName(groupForm.getName());
        if (groupForm.getAppIds() != null) {
            group.setApps(
                    groupForm.getAppIds().stream().map(id -> (App) appManager.getApp(id)).collect(Collectors.toList()));
        } else {
            group.setApps(new ArrayList<>());
        }
        dbConnection.update(group);
    }

    @Override
    public void deleteGroup(Group group) {
        deleteGroupForApps(group);
        dbConnection.deleteGroup(group);
    }

    protected void deleteGroupForApps(Group group) {
        if (group.getApps() != null && !group.getApps().isEmpty()) {
            dbConnection.deleteGroupForApps(group);
        }
    }

    protected void addAppsToGroup(Group group, GroupForm groupForm) {
        if (groupForm.getAppIds() != null) {
            group.setApps(
                    groupForm.getAppIds().stream().map(id -> (App) appManager.getApp(id)).collect(Collectors.toList()));

            dbConnection.update(group);
        }
    }
}
