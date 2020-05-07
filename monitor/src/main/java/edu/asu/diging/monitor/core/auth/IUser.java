package edu.asu.diging.monitor.core.auth;

public interface IUser {

    void setPassword(String password);

    String getPassword();

    void setUsername(String username);

    String getUsername();

}
