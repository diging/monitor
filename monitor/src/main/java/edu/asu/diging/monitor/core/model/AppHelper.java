package edu.asu.diging.monitor.core.model;

import edu.asu.diging.monitor.web.admin.forms.AppForm;

public class AppHelper {
	public static void copyAppInfo(IApp app, AppForm appForm) {
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
