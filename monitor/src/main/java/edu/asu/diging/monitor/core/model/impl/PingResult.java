package edu.asu.diging.monitor.core.model.impl;

import java.time.OffsetDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import edu.asu.diging.monitor.core.model.IPingResult;

@Entity
public class PingResult implements IPingResult {

	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE) private Long id;
	private OffsetDateTime pingTime;
	private int returnCode;
	@Lob private String returnValue;
	
	/* (non-Javadoc)
	 * @see edu.asu.diging.monitor.core.model.impl.IPingResult#getId()
	 */
	@Override
	public Long getId() {
		return id;
	}
	/* (non-Javadoc)
	 * @see edu.asu.diging.monitor.core.model.impl.IPingResult#setId(java.lang.String)
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	/* (non-Javadoc)
	 * @see edu.asu.diging.monitor.core.model.impl.IPingResult#getPingTime()
	 */
	@Override
	public OffsetDateTime getPingTime() {
		return pingTime;
	}
	/* (non-Javadoc)
	 * @see edu.asu.diging.monitor.core.model.impl.IPingResult#setPingTime(java.time.OffsetDateTime)
	 */
	@Override
	public void setPingTime(OffsetDateTime pingTime) {
		this.pingTime = pingTime;
	}
	/* (non-Javadoc)
	 * @see edu.asu.diging.monitor.core.model.impl.IPingResult#getReturnCode()
	 */
	@Override
	public int getReturnCode() {
		return returnCode;
	}
	/* (non-Javadoc)
	 * @see edu.asu.diging.monitor.core.model.impl.IPingResult#setReturnCode(int)
	 */
	@Override
	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}
	/* (non-Javadoc)
	 * @see edu.asu.diging.monitor.core.model.impl.IPingResult#getReturnValue()
	 */
	@Override
	public String getReturnValue() {
		return returnValue;
	}
	/* (non-Javadoc)
	 * @see edu.asu.diging.monitor.core.model.impl.IPingResult#setReturnValue(java.lang.String)
	 */
	@Override
	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}
	
}
