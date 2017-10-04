package edu.asu.diging.monitor.core.model.impl;

import javax.persistence.Entity;
import javax.persistence.Id;

import edu.asu.diging.monitor.core.model.INotificationRecipient;

@Entity
public class NotificationRecipient implements INotificationRecipient {

	private String name;
	@Id private String email;
	
	/* (non-Javadoc)
	 * @see edu.asu.diging.monitor.core.model.impl.INotificationRecipient#getName()
	 */
	@Override
	public String getName() {
		return name;
	}
	/* (non-Javadoc)
	 * @see edu.asu.diging.monitor.core.model.impl.INotificationRecipient#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}
	/* (non-Javadoc)
	 * @see edu.asu.diging.monitor.core.model.impl.INotificationRecipient#getEmail()
	 */
	@Override
	public String getEmail() {
		return email;
	}
	/* (non-Javadoc)
	 * @see edu.asu.diging.monitor.core.model.impl.INotificationRecipient#setEmail(java.lang.String)
	 */
	@Override
	public void setEmail(String email) {
		this.email = email;
	}
}
