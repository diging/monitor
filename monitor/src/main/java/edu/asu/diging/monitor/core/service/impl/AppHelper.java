package edu.asu.diging.monitor.core.service.impl;

import org.springframework.stereotype.Service;
import edu.asu.diging.monitor.core.model.IApp;
import edu.asu.diging.monitor.core.service.IAppHelper;
import edu.asu.diging.monitor.web.admin.forms.AppForm;

@Service
public class AppHelper implements IAppHelper {

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
	}
}
