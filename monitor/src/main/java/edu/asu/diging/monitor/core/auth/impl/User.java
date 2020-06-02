package edu.asu.diging.monitor.core.auth.impl;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import edu.asu.diging.monitor.core.model.impl.App;

@Entity
public class User {

    @Id
    private String username;

    private String password;
    
    @OneToOne(mappedBy = "user")
    private App app;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
