package edu.asu.diging.monitor.core.model;

import java.time.OffsetDateTime;

public interface IPingResult {

	Long getId();

	void setId(Long id);

	OffsetDateTime getPingTime();

	void setPingTime(OffsetDateTime pingTime);

	int getReturnCode();

	void setReturnCode(int returnCode);

	String getReturnValue();

	void setReturnValue(String returnValue);

}