package edu.asu.diging.monitor.core.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.diging.monitor.core.exceptions.GroupNotFoundException;
import edu.asu.diging.monitor.core.model.GroupType;
import edu.asu.diging.monitor.core.model.IApp;
import edu.asu.diging.monitor.core.model.impl.NotificationRecipient;
import edu.asu.diging.monitor.core.service.IAppHelper;
import edu.asu.diging.monitor.core.service.IGroupManager;
import edu.asu.diging.monitor.core.service.INotificationManager;
import edu.asu.diging.monitor.web.admin.forms.AppForm;
import edu.asu.diging.monitor.web.admin.forms.RecipientForm;

@Service
public class AppHelper implements IAppHelper {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private INotificationManager manager;

    @Autowired
    private IGroupManager groupManager;

    @Override
    public IApp copyAppInfo(IApp app, AppForm appForm) throws GroupNotFoundException {
        app.setDescription(appForm.getDescription());
        app.setExpectedReturnCodes(appForm.getExpectedReturnCodes());
        app.setHealthUrl(appForm.getHealthUrl());
        app.setName(appForm.getName());
        app.setRetries(appForm.getRetries());
        app.setTimeout(appForm.getTimeout());
        app.setWarningReturnCodes(appForm.getWarningReturnCodes());
        app.setPingInterval(appForm.getPingInterval());
        app.setMethod(appForm.getMethod());
        if (appForm.getRecipientIds() != null) {
            app.setRecipients(getRecipientsById(appForm.getRecipientIds()));
        }
        if (appForm.getGroupType() == GroupType.NEW) {
            app.setGroup(groupManager.createGroup(appForm.getGroupId()));
        } else {
            app.setGroup(groupManager.getGroup(appForm.getExistingGroupId()));
        }
        return app;
    }

    @Override
    public void copyAppInfoToForm(IApp app, AppForm appForm) {
        appForm.setDescription(app.getDescription());
        appForm.setExpectedReturnCodes(app.getExpectedReturnCodes());
        appForm.setHealthUrl(app.getHealthUrl());
        appForm.setId(app.getId());
        appForm.setMethod(app.getMethod());
        appForm.setName(app.getName());
        appForm.setPingInterval(app.getPingInterval());
        appForm.setRecipients(manager.getAllRecipients().stream().map(r -> {
            RecipientForm recipientForm = new RecipientForm();
            recipientForm.setName(r.getName());
            recipientForm.setEmail(r.getEmail());
            return recipientForm;
        }).collect(Collectors.toList()));
        appForm.setGroupType(GroupType.EXISTING);
        appForm.setGroupIds(groupManager.getGroups().stream()
                .collect(Collectors.toMap(g -> g.getId(), g -> g.getName())).entrySet());
        appForm.setExistingGroupId(app.getGroup().getId());
        appForm.setRetries(app.getRetries());
        appForm.setTimeout(app.getTimeout());
        appForm.setWarningReturnCodes(app.getWarningReturnCodes());
    }

    private List<NotificationRecipient> getRecipientsById(List<String> recipientIds) {
        return recipientIds.stream().map(id -> {
            return (NotificationRecipient) manager.getRecipient(id);
        }).collect(Collectors.toList());
    }
}
