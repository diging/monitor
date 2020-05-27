package edu.asu.diging.monitor.core.service.impl;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.diging.monitor.core.db.IAppDbConnection;
import edu.asu.diging.monitor.core.exceptions.GroupNotFoundException;
import edu.asu.diging.monitor.core.exceptions.UnstorableObjectException;
import edu.asu.diging.monitor.core.model.IApp;
import edu.asu.diging.monitor.core.model.impl.Group;
import edu.asu.diging.monitor.core.service.IGroupManager;

@Service
@Transactional
public class GroupManager implements IGroupManager {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IAppDbConnection dbConnection;

    @Override
    public Group createGroup(String name) {
        Group group = new Group();
        group.setName(name);
        group.setId(dbConnection.generateGroupId());
        try {
            dbConnection.createGroup(group);
        } catch (UnstorableObjectException e) {
            logger.error("Could not store group", e);
        }
        return group;
    }

    @Override
    public Group getGroup(String id) throws GroupNotFoundException {
        return dbConnection.getGroupById(id);
    }

    @Override
    public List<Group> getGroups() {
        Group[] groups = dbConnection.getAllGroups();
        return Arrays.asList(groups);
    }
}