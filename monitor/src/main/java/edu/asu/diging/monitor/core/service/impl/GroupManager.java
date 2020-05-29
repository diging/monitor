package edu.asu.diging.monitor.core.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.diging.monitor.core.db.IGroupDbConnection;
import edu.asu.diging.monitor.core.exceptions.GroupNotFoundException;
import edu.asu.diging.monitor.core.exceptions.UnstorableObjectException;
import edu.asu.diging.monitor.core.model.impl.Group;
import edu.asu.diging.monitor.core.service.IGroupManager;

@Service
@Transactional
public class GroupManager implements IGroupManager {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IGroupDbConnection dbConnection;

    @Override
    public Group createGroup(String name) throws GroupNotFoundException {
        Group group = new Group();
        group.setName(name);
        group.setId(dbConnection.generateGroupId());
        try {
            dbConnection.createGroup(group);
        } catch (UnstorableObjectException e) {
            logger.error("Could not store group", e);
            throw new GroupNotFoundException("Group couldn't be created", e);
        }
        return group;
    }

    @Override
    public Group getGroup(String id) throws GroupNotFoundException {
        return dbConnection.getGroupById(id);
    }

    @Override
    public List<Group> getGroups() {
        return dbConnection.getAllGroups();
    }
}
