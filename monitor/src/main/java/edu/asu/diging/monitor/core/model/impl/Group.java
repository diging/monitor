package edu.asu.diging.monitor.core.model.impl;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "AppGroup")
public class Group {

    @Id
    @Column(name = "groupId")
    private String id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "group", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<App> apps;

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

    public List<App> getApps() {
        return apps;
    }

    public void setApps(List<App> apps) {
        this.apps = apps;
    }

}
