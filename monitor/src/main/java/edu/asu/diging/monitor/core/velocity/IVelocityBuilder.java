package edu.asu.diging.monitor.core.velocity;

import java.util.Map;

public interface IVelocityBuilder {

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.velocity.impl.IVelocityBuilder#getRenderedTemplate(java.lang.String, java.util.Map)
	 */
	String getRenderedTemplate(String templateName, Map<String, Object> contextProperties);

}