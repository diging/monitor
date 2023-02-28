package edu.asu.diging.monitor.config;

import java.util.List;
import java.util.concurrent.Executor;

import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@PropertySource("classpath:config.properties")
@EnableAsync
@EnableScheduling
@EnableWebMvc
public class AppConfig implements AsyncConfigurer, WebMvcConfigurer {
    
    @Autowired
    private Environment env; 


	@Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(11);
        executor.setThreadNamePrefix("AppChecker-");
        executor.initialize();
        return executor;
    }
	
	@Bean
	public TaskScheduler taskScheduler() {
	    return new ConcurrentTaskScheduler(); //single threaded by default
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		// TODO: return custom exception handler
		return new SimpleAsyncUncaughtExceptionHandler();
	}

	@Bean
	public AES256TextEncryptor encryptor() {
	    AES256TextEncryptor encryptor = new AES256TextEncryptor();
	    encryptor.setPassword(env.getRequiredProperty("encryption.password"));
	    return encryptor;
	}
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
	    converters.add(new MappingJackson2HttpMessageConverter());
	}

}
