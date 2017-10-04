package edu.asu.diging.monitor.core.model;

import java.time.OffsetDateTime;
import java.util.List;

public interface IAppTest {

	String getId();

	void setId(String id);

	OffsetDateTime getPingTime();

	void setPingTime(OffsetDateTime pingTime);

	AppStatus getStatus();

	void setStatus(AppStatus status);

	List<IPingResult> getPingResults();

	void setPingResults(List<IPingResult> pingResults);

	String getAppId();

	void setAppId(String appId);

}