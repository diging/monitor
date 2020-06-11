package edu.asu.diging.monitor.core.service;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.diging.monitor.web.admin.forms.AppForm;
import edu.asu.diging.monitor.web.admin.forms.GroupForm;

public interface IGroupHelper {
    boolean isValid(AppForm appForm, RedirectAttributes redirectAttrs);

    boolean isGroupNameValid(GroupForm groupForm, RedirectAttributes redirectAttrs);

    boolean isUpdateInfoValid(AppForm appForm, RedirectAttributes redirectAttrs);
}
