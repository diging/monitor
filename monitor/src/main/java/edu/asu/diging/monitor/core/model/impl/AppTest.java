package edu.asu.diging.monitor.core.model.impl;

import java.time.OffsetDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.asu.diging.monitor.core.model.AppStatus;
import edu.asu.diging.monitor.core.model.IAppTest;
import edu.asu.diging.monitor.core.model.IPingResult;

@Entity
@Table(indexes={
        @Index(columnList="appId", name="IDX_APP_ID")
})
public class AppTest implements IAppTest {

	@Id private String id;
	private OffsetDateTime pingTime;
	private AppStatus status;
	@JsonIgnore
	@OneToMany(targetEntity=PingResult.class) @Cascade({CascadeType.ALL}) private List<IPingResult> pingResults;
	private String appId;
	
	/* (non-Javadoc)
	 * @see edu.asu.diging.monitor.core.model.impl.IAppTest#getId()
	 */
	@Override
	public String getId() {
		return id;
	}
	/* (non-Javadoc)
	 * @see edu.asu.diging.monitor.core.model.impl.IAppTest#setId(java.lang.String)
	 */
	@Override
	public void setId(String id) {
		this.id = id;
	}
	/* (non-Javadoc)
	 * @see edu.asu.diging.monitor.core.model.impl.IAppTest#getPingTime()
	 */
	@Override
	public OffsetDateTime getPingTime() {
		return pingTime;
	}
	/* (non-Javadoc)
	 * @see edu.asu.diging.monitor.core.model.impl.IAppTest#setPingTime(java.time.OffsetDateTime)
	 */
	@Override
	public void setPingTime(OffsetDateTime pingTime) {
		this.pingTime = pingTime;
	}
	/* (non-Javadoc)
	 * @see edu.asu.diging.monitor.core.model.impl.IAppTest#getStatus()
	 */
	@Override
	public AppStatus getStatus() {
		return status;
	}
	/* (non-Javadoc)
	 * @see edu.asu.diging.monitor.core.model.impl.IAppTest#setStatus(edu.asu.diging.monitor.core.model.AppStatus)
	 */
	@Override
	public void setStatus(AppStatus status) {
		this.status = status;
	}
	/* (non-Javadoc)
	 * @see edu.asu.diging.monitor.core.model.impl.IAppTest#getPingResults()
	 */
	@Override
	public List<IPingResult> getPingResults() {
		return pingResults;
	}
	/* (non-Javadoc)
	 * @see edu.asu.diging.monitor.core.model.impl.IAppTest#setPingResults(java.util.List)
	 */
	@Override
	public void setPingResults(List<IPingResult> pingResults) {
		this.pingResults = pingResults;
	}
	/* (non-Javadoc)
	 * @see edu.asu.diging.monitor.core.model.impl.IAppTest#getAppId()
	 */
	@Override
	public String getAppId() {
		return appId;
	}
	/* (non-Javadoc)
	 * @see edu.asu.diging.monitor.core.model.impl.IAppTest#setAppId(java.lang.String)
	 */
	@Override
	public void setAppId(String appId) {
		this.appId = appId;
	}
}
