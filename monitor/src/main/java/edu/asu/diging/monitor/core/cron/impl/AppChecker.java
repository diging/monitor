package edu.asu.diging.monitor.core.cron.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import edu.asu.diging.monitor.core.cron.IAppChecker;
import edu.asu.diging.monitor.core.model.AppStatus;
import edu.asu.diging.monitor.core.model.IApp;
import edu.asu.diging.monitor.core.model.IAppTest;
import edu.asu.diging.monitor.core.model.IPingResult;
import edu.asu.diging.monitor.core.model.impl.AppTest;
import edu.asu.diging.monitor.core.model.impl.PingResult;
import edu.asu.diging.monitor.core.service.IAppManager;
import edu.asu.diging.monitor.core.service.INotificationManager;
import edu.asu.diging.monitor.core.service.IPasswordEncryptor;

@Component
@PropertySource(value = "classpath:/config.properties")
public class AppChecker implements IAppChecker {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${ping.connection.timeout}")
    private String pingConnectionTimeout;

    @Autowired
    private IAppManager appManager;

    @Autowired
    private INotificationManager notificationManager;

    @Autowired
    private IPasswordEncryptor passwordDecryptor;

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.asu.diging.monitor.core.cron.impl.IAppChecker#checkApp(edu.asu.diging.
     * monitor.core.model.IApp)
     */
    @Override
    @Async
    public void checkApp(IApp app) {
        logger.info("Checking app: " + app.getName());

        IAppTest lastTest = app.getLastAppTest();

        IAppTest test = new AppTest();
        app.setLastAppTest(test);
        test.setStatus(AppStatus.IN_PROGRESS);
        test.setAppId(app.getId());
        OffsetDateTime execTime = OffsetDateTime.now();
        test.setPingTime(execTime);

        IPingResult pingResult = new PingResult();
        pingResult.setPingTime(execTime);
        test.setPingResults(new ArrayList<>());
        test.getPingResults().add(pingResult);
        String authHeaderValue = null;
        if (app.getUsername() != null && app.getPassword() != null) {
            String password = passwordDecryptor.decrypt(app.getPassword());
            String auth = app.getUsername() + ":" + password;
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
            authHeaderValue = "Basic " + new String(encodedAuth);
        }
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(app.getHealthUrl()).openConnection();
            connection.setRequestMethod(app.getMethod() != null ? app.getMethod().toString() : "HEAD");
            if (authHeaderValue != null) {
                connection.setRequestProperty("Authorization", authHeaderValue);
            }
            int timeout = app.getTimeout() > 0 ? app.getTimeout() : new Integer(pingConnectionTimeout);
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            int responseCode = connection.getResponseCode();
            String responseMsg = connection.getResponseMessage();

            pingResult.setReturnCode(responseCode);
            pingResult.setReturnValue(responseMsg);
            test.setStatus(determineStatus(app, responseCode));

        } catch (IOException e) {
            logger.error("Could not open connection.", e);
            test.setStatus(AppStatus.ERROR);
            pingResult.setReturnValue(e.getMessage());
        }

        logger.info("App status for " + app.getName() + " is " + test.getStatus());
        appManager.addAppTest(test, true);

        if (lastTest != null && (lastTest.getStatus() != test.getStatus())) {
            notificationManager.sendNotificationEmails(app, lastTest.getStatus());
        }
    }

    private AppStatus determineStatus(IApp app, int responseCode) {
        if (app.getExpectedReturnCodes() == null || app.getExpectedReturnCodes().trim().isEmpty()) {
            // by default we accept any response codes in the 200 range
            if (responseCode >= 200 && responseCode < 300) {
                return AppStatus.OK;
            } else if (responseCode >= 300 && responseCode < 400) {
                // anything in 300 range should be a warning (redirection)
                return AppStatus.WARNING;
            } else {
                // everything else is an error
                return AppStatus.ERROR;
            }
        } else {
            List<String> expectedCodes = Arrays.asList(app.getExpectedReturnCodes().split(","));
            expectedCodes = expectedCodes.stream().map(c -> c.trim()).collect(Collectors.toList());
            List<String> warningCodes = Arrays.asList(app.getWarningReturnCodes().split(","));
            warningCodes = warningCodes.stream().map(c -> c.trim()).collect(Collectors.toList());
            if (expectedCodes.contains(responseCode + "")) {
                return AppStatus.OK;
            } else if (warningCodes.contains(responseCode + "")) {
                return AppStatus.WARNING;
            } else {
                return AppStatus.ERROR;
            }
        }
    }
}
