package edu.asu.diging.monitor.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.diging.monitor.core.db.ITagDbConnection;
import edu.asu.diging.monitor.core.exceptions.UnstorableObjectException;
import edu.asu.diging.monitor.core.model.ITag;
import edu.asu.diging.monitor.core.model.impl.Tag;
import edu.asu.diging.monitor.core.service.ITagManager;

@Service
@Transactional
public class TagManager implements ITagManager {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ITagDbConnection dbConnection;

    @Override
    public List<Tag> getTagList(String query) {
        List<Tag> tags = dbConnection.getAllTags();
        if (tags != null) {
            return tags;
        }
        return new ArrayList<Tag>();
    }

    @Override
    public ITag getTag(String id) {
        return dbConnection.getById(id);
    }
    
    @Override
    public Tag getTagByName(String name) {
        return dbConnection.getTagByName(name);
    }

    @Override
    public boolean addTags(List<Tag> tags) {
        try {
            dbConnection.store(tags);
        } catch (UnstorableObjectException e) {
            logger.error("Could not store app", e);
            return false;
        }
        return true;
    }
    
}
