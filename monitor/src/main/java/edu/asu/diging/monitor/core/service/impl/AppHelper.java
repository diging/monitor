package edu.asu.diging.monitor.core.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.asu.diging.monitor.core.model.IApp;
import edu.asu.diging.monitor.core.model.impl.NotificationRecipient;
import edu.asu.diging.monitor.core.service.IAppHelper;
import edu.asu.diging.monitor.core.service.INotificationManager;
import edu.asu.diging.monitor.web.admin.forms.AppForm;

@Service
public class AppHelper implements IAppHelper {

    @Autowired
    private INotificationManager manager;

    @Override
    public void copyAppInfo(IApp app, AppForm appForm) {
        app.setDescription(appForm.getDescription());
        app.setExpectedReturnCodes(appForm.getExpectedReturnCodes());
        app.setHealthUrl(appForm.getHealthUrl());
        app.setName(appForm.getName());
        app.setRetries(appForm.getRetries());
        app.setTimeout(appForm.getTimeout());
        app.setWarningReturnCodes(appForm.getWarningReturnCodes());
        app.setPingInterval(appForm.getPingInterval());
        app.setMethod(appForm.getMethod());
        if (appForm.getRecipientIds()!=null)
            app.setRecipients(getRecipientsById(appForm.getRecipientIds()));
    }

    private List<NotificationRecipient> getRecipientsById(List<String> recipientIds) {
        return recipientIds.stream().map(id -> {
            return (NotificationRecipient) manager.getRecipient(id);
        }).collect(Collectors.toList());
    }
}
