package edu.asu.diging.monitor.core.service;

import java.util.List;

import edu.asu.diging.monitor.core.model.ITag;

public interface ITagManager {
    List<ITag> getTagList(String query);

    ITag getTag(String id);

    ITag getTagByName(String name);

}
