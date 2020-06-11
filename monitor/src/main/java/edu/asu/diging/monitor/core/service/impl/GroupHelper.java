package edu.asu.diging.monitor.core.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.diging.monitor.core.model.GroupType;
import edu.asu.diging.monitor.core.service.IGroupHelper;
import edu.asu.diging.monitor.web.admin.forms.AppForm;
import edu.asu.diging.monitor.web.admin.forms.GroupForm;

@Service
public class GroupHelper implements IGroupHelper {

    @Override
    public boolean isValid(AppForm appForm, RedirectAttributes redirectAttrs) {
        boolean flag = true;
        if (appForm.getGroupType() == GroupType.NEW && appForm.getGroupName().trim().isEmpty()) {
            flag = false;
            redirectAttrs.addFlashAttribute("show_alert", true);
            redirectAttrs.addFlashAttribute("alert_type", "danger");
            redirectAttrs.addFlashAttribute("alert_msg", "App could not be Stored. New group name cannot be blank.");
        } else if (appForm.getGroupType() == GroupType.EXISTING && appForm.getExistingGroupId() == null) {
            flag = false;
            redirectAttrs.addFlashAttribute("show_alert", true);
            redirectAttrs.addFlashAttribute("alert_type", "danger");
            redirectAttrs.addFlashAttribute("alert_msg",
                    "App could not be Stored. Please select a valid group from dropdown. ");

        }
        return flag;
    }

    @Override
    public boolean isGroupNameValid(GroupForm groupForm, RedirectAttributes redirectAttrs) {
        boolean flag = true;
        if (groupForm.getName() == null || groupForm.getName().trim().isEmpty()) {
            flag = false;
            redirectAttrs.addFlashAttribute("show_alert", true);
            redirectAttrs.addFlashAttribute("alert_type", "danger");
            if (groupForm.getId() == null) {
                redirectAttrs.addFlashAttribute("alert_msg", "Group could not be stored. Group Name cannot be blank.");
            } else {
                redirectAttrs.addFlashAttribute("alert_msg", "Group could not be updated. Group Name cannot be blank.");
            }
        }
        return flag;
    }

    @Override
    public boolean isUpdateInfoValid(AppForm appForm, RedirectAttributes redirectAttrs) {
        boolean flag = true;
        if (appForm.getGroupType() == GroupType.NEW && appForm.getGroupName().trim().isEmpty()) {
            redirectAttrs.addFlashAttribute("show_alert", true);
            redirectAttrs.addFlashAttribute("alert_type", "danger");
            redirectAttrs.addFlashAttribute("alert_msg",
                    "App could not be updated. New group name field cannot be blank");
        }

        if (appForm.getGroupType() == GroupType.EXISTING && appForm.getExistingGroupId() == null) {
            redirectAttrs.addFlashAttribute("show_alert", true);
            redirectAttrs.addFlashAttribute("alert_type", "danger");
            redirectAttrs.addFlashAttribute("alert_msg",
                    "App could not be updated. Please select a valid group from dropdown");
        }
        return flag;
    }
}
