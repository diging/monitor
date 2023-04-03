package edu.asu.diging.monitor.core.model;

import java.util.List;

import edu.asu.diging.monitor.core.model.impl.App;

public interface ITag {

    Long getId();

    void setId(Long id);

    String getName();

    void setName(String name);
    
    List<App> getApps();
    
    void setApps(List<App> apps);

}
