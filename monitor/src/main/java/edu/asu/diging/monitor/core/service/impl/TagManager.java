package edu.asu.diging.monitor.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.diging.monitor.core.db.ITagDbConnection;
import edu.asu.diging.monitor.core.model.ITag;
import edu.asu.diging.monitor.core.service.ITagManager;

@Service
@Transactional
public class TagManager implements ITagManager {

    @Autowired
    private ITagDbConnection dbConnection;

    @Override
    public List<ITag> getTagList(String query) {
        List<ITag> tags = dbConnection.getAllTags(query);
        if (tags != null) {
            return tags;
        }
        return new ArrayList<ITag>();
    }

    @Override
    public ITag getTag(String id) {
        return dbConnection.getById(id);
    }

    @Override
    public ITag getTagByName(String name) {
        return dbConnection.getTagByName(name);
    }

}
