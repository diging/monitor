package edu.asu.diging.monitor.web.admin.forms;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import edu.asu.diging.monitor.core.model.GroupType;
import edu.asu.diging.monitor.core.model.RequestMethod;
import edu.asu.diging.monitor.core.model.impl.Tag;

public class AppForm {

    private String id;
    private String name;
    private String healthUrl;
    private String description;
    private String tagString;
    private List<String> tags;
    private String expectedReturnCodes;
    private String warningReturnCodes;
    private int timeout;
    private int retries;
    private int pingInterval;
    private RequestMethod method;
    private List<RecipientForm> recipients;
    private List<String> recipientIds;
    private String username;
    private String password;
    private boolean updateUserInfo;
    private Set<Entry<String, String>> groupIds;
    private String groupName;
    private String existingGroupId;
    private GroupType groupType;

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

    public String getHealthUrl() {
        return healthUrl;
    }

    public void setHealthUrl(String healthUrl) {
        this.healthUrl = healthUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpectedReturnCodes() {
        return expectedReturnCodes;
    }

    public void setExpectedReturnCodes(String expectedReturnCodes) {
        this.expectedReturnCodes = expectedReturnCodes;
    }

    public String getWarningReturnCodes() {
        return warningReturnCodes;
    }

    public void setWarningReturnCodes(String warningReturnCodes) {
        this.warningReturnCodes = warningReturnCodes;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

    public int getPingInterval() {
        return pingInterval;
    }

    public void setPingInterval(int pingInterval) {
        this.pingInterval = pingInterval;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public void setMethod(RequestMethod method) {
        this.method = method;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean isUpdateUserInfo() {
        return updateUserInfo;
    }

    public void setUpdateUserInfo(boolean updateUserInfo) {
        this.updateUserInfo = updateUserInfo;
    }

    public List<RecipientForm> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<RecipientForm> recipients) {
        this.recipients = recipients;
    }

    public List<String> getRecipientIds() {
        return recipientIds;
    }

    public void setRecipientIds(List<String> recipientIds) {
        this.recipientIds = recipientIds;
    }

    public Set<Entry<String, String>> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(Set<Entry<String, String>> groupIds) {
        this.groupIds = groupIds;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getExistingGroupId() {
        return existingGroupId;
    }

    public void setExistingGroupId(String existingGroupId) {
        this.existingGroupId = existingGroupId;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public void setGroupType(GroupType groupType) {
        this.groupType = groupType;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getTagString() {
        return tagString;
    }

    public void setTagString(String tagString) {
        this.tagString = tagString;
    }

}
