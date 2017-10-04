package edu.asu.diging.monitor.core.velocity.impl;

import java.io.StringWriter;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.stereotype.Service;

import edu.asu.diging.monitor.core.velocity.IVelocityBuilder;


@Service
public class VelocityBuilder implements IVelocityBuilder {
    
    private VelocityEngine engine;
    
    @PostConstruct
    public void init() throws Exception {
        engine = new VelocityEngine();
        engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        engine.setProperty("classpath.resource.loader.class",
                ClasspathResourceLoader.class.getName());
        engine.setProperty( RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,
                "org.apache.velocity.runtime.log.Log4JLogChute"
                );
        engine.setProperty("runtime.log.logsystem.log4j.logger","velocity");
        engine.init();
    }

    /* (non-Javadoc)
	 * @see edu.asu.diging.monitor.core.velocity.impl.IVelocityBuilder#getRenderedTemplate(java.lang.String, java.util.Map)
	 */
    @Override
    public String getRenderedTemplate(String templateName, Map<String, Object> contextProperties) {
        Template template = engine.getTemplate(templateName);
        VelocityContext context = new VelocityContext();
        
        for (String key : contextProperties.keySet()) {
            context.put(key, contextProperties.get(key));
        }
        
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer.toString();
    }
}