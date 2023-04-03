package edu.asu.diging.monitor.core.db;

import java.util.List;

import edu.asu.diging.monitor.core.model.ITag;

public interface ITagDbConnection {

    ITag getById(String id);

    List<ITag> getAllTags(String userQuery);

    ITag getTagByName(String name);

}
