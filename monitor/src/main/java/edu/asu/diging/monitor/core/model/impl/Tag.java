package edu.asu.diging.monitor.core.model.impl;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.asu.diging.monitor.core.model.ITag;

@Entity
@Table(name = "tags")
public class Tag implements ITag {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "app_tags", joinColumns = @JoinColumn(name = "app_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<App> apps;
    
    
    public List<App> getApps() {
        return apps;
    }

    public void setApps(List<App> apps) {
        this.apps = apps;
    }

    public Tag() {
        
    }
    
    public Tag(String name) {
        this.name = name;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    } 

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
