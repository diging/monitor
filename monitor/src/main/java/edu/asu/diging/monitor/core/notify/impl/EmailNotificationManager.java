package edu.asu.diging.monitor.core.notify.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import edu.asu.diging.monitor.core.model.IApp;
import edu.asu.diging.monitor.core.notify.IEmailNotificationManager;
import edu.asu.diging.monitor.core.velocity.IVelocityBuilder;

/**
 * This class manages all the outgoing emails from the Quadriga system.
 * 
 * @author Ram Kumar Kumaresan
 */
@Service
@PropertySource(value = "classpath:/config.properties")
public class EmailNotificationManager implements IEmailNotificationManager {

	private static final Logger logger = LoggerFactory.getLogger(EmailNotificationManager.class);

	@Autowired
	private EmailNotificationSender emailSender;

	@Autowired
	private IVelocityBuilder velocityBuilder;
	
	@Autowired
	private Environment env;

	/* (non-Javadoc)
	 * @see edu.asu.diging.monitor.core.notify.impl.IEmailNotificationManager#sendAppStatusNotificationEmail(java.lang.String, edu.asu.diging.monitor.core.model.IApp, java.lang.String, java.lang.String)
	 */
	@Override
	public void sendAppStatusNotificationEmail(String email, IApp app, String prevStatus, String newStatus) {
		Map<String, Object> contextProperties = new HashMap<>();
		contextProperties.put("app", app);
		contextProperties.put("prevStatus", prevStatus);
		contextProperties.put("newStatus", newStatus);
		
		String msg = velocityBuilder.getRenderedTemplate("/velocity/appStatusChanged.vm", contextProperties);
        emailSender.sendNotificationEmail(email,
                env.getProperty("notification.email.subject").replace("{app}", app.getName()).replace("{status}", newStatus),
                msg);
        logger.info("Notification email sent to " + email);   
    }

}