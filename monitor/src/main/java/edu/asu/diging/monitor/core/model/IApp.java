package edu.asu.diging.monitor.core.model;

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

}