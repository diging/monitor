package edu.asu.diging.monitor.core.service;


import edu.asu.diging.monitor.core.exceptions.GroupNotFoundException;
import edu.asu.diging.monitor.core.exceptions.UnstorableObjectException;
import edu.asu.diging.monitor.core.model.IApp;
import edu.asu.diging.monitor.web.admin.forms.AppForm;

public interface IAppHelper {

    IApp copyAppInfo(IApp app, AppForm appForm) throws GroupNotFoundException, UnstorableObjectException;

    void copyAppInfoToForm(IApp app, AppForm appForm);

    void copyGroupAndRecipientInfoToForm(AppForm appForm);

}
