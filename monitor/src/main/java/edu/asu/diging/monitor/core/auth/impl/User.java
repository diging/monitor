package edu.asu.diging.monitor.core.auth.impl;

import javax.persistence.Entity;
import javax.persistence.Id;

import edu.asu.diging.monitor.core.auth.IUser;

@Entity
public class User implements IUser {

    @Id
    private String username;

    private String password;

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

}
