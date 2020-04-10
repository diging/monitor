package edu.asu.diging.monitor.core.model.impl;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import edu.asu.diging.monitor.core.model.INotificationRecipient;

@Entity
public class NotificationRecipient implements INotificationRecipient {

    private String name;
    @Id
    private String email;

    @ManyToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JoinTable(name = "AppNotificationRecipients", joinColumns = { @JoinColumn(name = "email") }, inverseJoinColumns = {
            @JoinColumn(name = "id") })
    private List<App> apps;

    public List<App> getApps() {
        return apps;
    }

    public void setApps(List<App> apps) {
        this.apps = apps;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.diging.monitor.core.model.impl.INotificationRecipient#getName()
     */
    @Override
    public String getName() {
        return name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.asu.diging.monitor.core.model.impl.INotificationRecipient#setName(java.
     * lang.String)
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.diging.monitor.core.model.impl.INotificationRecipient#getEmail()
     */
    @Override
    public String getEmail() {
        return email;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.asu.diging.monitor.core.model.impl.INotificationRecipient#setEmail(java.
     * lang.String)
     */
    @Override
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString() {
        return name;
    }
}
