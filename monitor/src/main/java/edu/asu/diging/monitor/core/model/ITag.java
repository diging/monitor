package edu.asu.diging.monitor.core.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


public interface ITag {
    
    Long getId();
    
    void setId(Long id);
    
    String getName();
    
    void setName(String name);
    
}
