package edu.asu.diging.monitor.core.db;

import java.util.List;

import edu.asu.diging.monitor.core.exceptions.UnstorableObjectException;
import edu.asu.diging.monitor.core.model.ITag;
import edu.asu.diging.monitor.core.model.impl.Tag;

public interface ITagDbConnection {

    ITag getById(String id);

    ITag store(ITag tag) throws UnstorableObjectException;

    void delete(ITag tag);

    List<Tag> getAllTags();

}
