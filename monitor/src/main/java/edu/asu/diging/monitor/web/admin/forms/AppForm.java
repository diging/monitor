package edu.asu.diging.monitor.web.admin.forms;

import java.util.List;

import edu.asu.diging.monitor.core.model.RequestMethod;

public class AppForm {

	private String id;
	private String name;
	private String healthUrl;
	private String description;
	private String expectedReturnCodes;
	private String warningReturnCodes;
	private int timeout;
	private int retries;
	private int pingInterval;
	private RequestMethod method;
	private List<RecipientForm> recipients;
	private List<String> recipientIds;

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

}
