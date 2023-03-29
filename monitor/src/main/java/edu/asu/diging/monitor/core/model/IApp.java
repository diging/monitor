package edu.asu.diging.monitor.core.model;

import java.util.List;

import edu.asu.diging.monitor.core.model.impl.Group;
import edu.asu.diging.monitor.core.model.impl.NotificationRecipient;
import edu.asu.diging.monitor.core.model.impl.Tag;

public interface IApp {

    String getId();

    void setId(String id);

    String getName();

    void setName(String name);

    String getHealthUrl();

    void setHealthUrl(String healthUrl);

    String getDescription();

    void setDescription(String description);

    String getExpectedReturnCodes();

    void setExpectedReturnCodes(String expectedReturnCodes);

    String getWarningReturnCodes();

    void setWarningReturnCodes(String warningReturnCodes);

    int getTimeout();

    void setTimeout(int timeout);

    int getRetries();

    void setRetries(int retries);

    void setPingInterval(int pingInterval);

    int getPingInterval();

    void setLastTestId(String lastTestId);

    String getLastTestId();

    void setLastAppTest(IAppTest lastAppTest);

    IAppTest getLastAppTest();

    void setMethod(RequestMethod method);

    RequestMethod getMethod();

    List<NotificationRecipient> getRecipients();

    void setRecipients(List<NotificationRecipient> recipients);

    String getPassword();

    void setPassword(String password);

    String getUsername();

    void setUsername(String username);

    void setGroup(Group group);

    Group getGroup();
    
    List<Tag> getTags();

    void setTags(List<Tag> tags);

}