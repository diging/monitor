package edu.asu.diging.monitor.web.admin;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.diging.monitor.core.exceptions.GroupNotFoundException;
import edu.asu.diging.monitor.core.model.GroupType;
import edu.asu.diging.monitor.core.model.IApp;
import edu.asu.diging.monitor.core.service.IAppHelper;
import edu.asu.diging.monitor.core.service.IAppManager;
import edu.asu.diging.monitor.web.admin.forms.AppForm;

@Controller
public class ModifyAppController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IAppManager appManager;

    @Autowired
    private IAppHelper appHelper;

    @RequestMapping(value = "/admin/apps/{id}/modify", method = RequestMethod.GET)
    public String show(Model model, @PathVariable("id") String id) {
        AppForm appForm = new AppForm();
        IApp app = appManager.getApp(id);
        appHelper.copyAppInfoToForm(app, appForm);
        model.addAttribute("appForm", appForm);
        model.addAttribute("appRecipients",
                app.getRecipients().stream().map(x -> x.getEmail()).collect(Collectors.toList()));
        return "admin/apps/show";
    }

    @RequestMapping(value = "/admin/apps/{id}/modify", method = RequestMethod.POST)
    public String update(@ModelAttribute AppForm appForm, @PathVariable("id") String id,
            RedirectAttributes redirectAttrs) {
        if (appForm.getGroupType() == null
                || appForm.getGroupType() == GroupType.NEW && appForm.getGroupName().trim().isEmpty()
                || appForm.getGroupType() == GroupType.EXISTING && appForm.getExistingGroupId() == null) {
            redirectAttrs.addFlashAttribute("show_alert", true);
            redirectAttrs.addFlashAttribute("alert_type", "danger");
            redirectAttrs.addFlashAttribute("alert_msg",
                    "App could not be updated. Please create a group for this app or select from an existing one.");
            return "redirect:/";
        }
        IApp app = appManager.getApp(id);
        if (app.getRecipients() != null && !app.getRecipients().isEmpty()) {
            appManager.deleteExistingRecipients(app);
        }
        try {
            app = appHelper.copyAppInfo(appManager.getApp(id), appForm);
        } catch (GroupNotFoundException e) {
            logger.error("Could not find Group", e);
            redirectAttrs.addFlashAttribute("show_alert", true);
            redirectAttrs.addFlashAttribute("alert_type", "danger");
            redirectAttrs.addFlashAttribute("alert_msg",
                    "The selected group doesn't exist. Please create a group for this app or select from an existing one. ");
            return "redirect:/";

        }
        appManager.updateApp(app);
        redirectAttrs.addFlashAttribute("show_alert", true);
        redirectAttrs.addFlashAttribute("alert_type", "success");
        redirectAttrs.addFlashAttribute("alert_msg", "App was successfully updated.");
        return "redirect:/";
    }
}
