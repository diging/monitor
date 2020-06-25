package edu.asu.diging.monitor.web.admin.forms;

import java.util.List;

public class GroupForm {

    private String id;
    private String name;
    private List<AppForm> apps;
    private List<String> appIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AppForm> getApps() {
        return apps;
    }

    public void setApps(List<AppForm> apps) {
        this.apps = apps;
    }

    public List<String> getAppIds() {
        return appIds;
    }

    public void setAppIds(List<String> appIds) {
        this.appIds = appIds;
    }

}
