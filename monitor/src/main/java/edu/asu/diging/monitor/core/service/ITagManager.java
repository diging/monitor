package edu.asu.diging.monitor.core.service;

import java.util.List;

import edu.asu.diging.monitor.core.model.ITag;
import edu.asu.diging.monitor.core.model.impl.Tag;

public interface ITagManager {
    List getTagList(String query);
    
    ITag addTag(Tag tag);

    ITag getTag(String id);

}