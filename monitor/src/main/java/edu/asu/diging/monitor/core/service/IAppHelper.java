package edu.asu.diging.monitor.core.service;

import edu.asu.diging.monitor.core.model.IApp;
import edu.asu.diging.monitor.web.admin.forms.AppForm;

public interface IAppHelper {

	void copyAppInfo(IApp app, AppForm appForm);

}
