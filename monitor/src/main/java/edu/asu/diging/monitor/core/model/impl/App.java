package edu.asu.diging.monitor.core.model.impl;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import edu.asu.diging.monitor.core.model.IApp;
import edu.asu.diging.monitor.core.model.IAppTest;
import edu.asu.diging.monitor.core.model.RequestMethod;

@Entity
public class App implements IApp {

    @Id
    private String id;
    private String name;
    private String healthUrl;
    private String description;
    private String expectedReturnCodes;
    private String warningReturnCodes;
    private int timeout;
    private int retries;
    private int pingInterval;
    private String lastTestId;
    @ManyToMany(mappedBy = "apps")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Tag> tags;
    @Transient
    private IAppTest lastAppTest;
    private RequestMethod method;
    @ManyToMany(mappedBy = "apps", fetch = FetchType.EAGER)
    private List<NotificationRecipient> recipients;
    private String username;
    private String password;

    @ManyToOne
    @JoinColumn(name = "groupId", nullable = true)
    private Group group;

    @Override
    public String toString() {
        return name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.diging.monitor.core.model.impl.IApp#getId()
     */
    @Override
    public String getId() {
        return id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.diging.monitor.core.model.impl.IApp#setId(java.lang.String)
     */
    @Override
    public void setId(String id) {
        this.id = id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.diging.monitor.core.model.impl.IApp#getName()
     */
    @Override
    public String getName() {
        return name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.diging.monitor.core.model.impl.IApp#setName(java.lang.String)
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.diging.monitor.core.model.impl.IApp#getHealthUrl()
     */
    @Override
    public String getHealthUrl() {
        return healthUrl;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.asu.diging.monitor.core.model.impl.IApp#setHealthUrl(java.lang.String)
     */
    @Override
    public void setHealthUrl(String healthUrl) {
        this.healthUrl = healthUrl;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.diging.monitor.core.model.impl.IApp#getDescription()
     */
    @Override
    public String getDescription() {
        return description;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.asu.diging.monitor.core.model.impl.IApp#setDescription(java.lang.String)
     */
    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.diging.monitor.core.model.impl.IApp#getExpectedReturnCodes()
     */
    @Override
    public String getExpectedReturnCodes() {
        return expectedReturnCodes;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.asu.diging.monitor.core.model.impl.IApp#setExpectedReturnCodes(java.lang.
     * String)
     */
    @Override
    public void setExpectedReturnCodes(String expectedReturnCodes) {
        this.expectedReturnCodes = expectedReturnCodes;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.diging.monitor.core.model.impl.IApp#getWarningReturnCodes()
     */
    @Override
    public String getWarningReturnCodes() {
        return warningReturnCodes;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.asu.diging.monitor.core.model.impl.IApp#setWarningReturnCodes(java.lang.
     * String)
     */
    @Override
    public void setWarningReturnCodes(String warningReturnCodes) {
        this.warningReturnCodes = warningReturnCodes;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.diging.monitor.core.model.impl.IApp#getTimeout()
     */
    @Override
    public int getTimeout() {
        return timeout;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.diging.monitor.core.model.impl.IApp#setTimeout(int)
     */
    @Override
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.diging.monitor.core.model.impl.IApp#getRetries()
     */
    @Override
    public int getRetries() {
        return retries;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.diging.monitor.core.model.impl.IApp#setRetries(int)
     */
    @Override
    public void setRetries(int retries) {
        this.retries = retries;
    }

    @Override
    public int getPingInterval() {
        return pingInterval;
    }

    @Override
    public void setPingInterval(int pingInterval) {
        this.pingInterval = pingInterval;
    }

    @Override
    public String getLastTestId() {
        return lastTestId;
    }

    @Override
    public void setLastTestId(String lastTestId) {
        this.lastTestId = lastTestId;
    }
    
    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public IAppTest getLastAppTest() {
        return lastAppTest;
    }

    @Override
    public void setLastAppTest(IAppTest lastAppTest) {
        this.lastAppTest = lastAppTest;
    }

    @Override
    public RequestMethod getMethod() {
        return method;
    }

    @Override
    public void setMethod(RequestMethod method) {
        this.method = method;
    }

    @Override
    public List<NotificationRecipient> getRecipients() {
        return recipients;
    }

    @Override
    public void setRecipients(List<NotificationRecipient> recipients) {
        this.recipients = recipients;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Group getGroup() {
        return group;
    }

    @Override
    public void setGroup(Group group) {
        this.group = group;
    }
}
