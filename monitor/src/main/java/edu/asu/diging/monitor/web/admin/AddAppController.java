package edu.asu.diging.monitor.web.admin;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.diging.monitor.core.model.GroupType;
import edu.asu.diging.monitor.core.model.IApp;
import edu.asu.diging.monitor.core.model.impl.App;
import edu.asu.diging.monitor.core.service.IAppHelper;
import edu.asu.diging.monitor.core.service.IAppManager;
import edu.asu.diging.monitor.core.service.INotificationManager;
import edu.asu.diging.monitor.web.admin.forms.AppForm;
import edu.asu.diging.monitor.web.admin.forms.RecipientForm;

@Controller
public class AddAppController {

    @Autowired
    private IAppManager appManager;

    @Autowired
    private IAppHelper appHelper;

    @Autowired
    private INotificationManager manager;

    @RequestMapping(value = "/admin/apps/add")
    public String show(Model model) {
        AppForm appForm = new AppForm();
        model.addAttribute("appForm", appForm);
        appForm.setRecipients(manager.getAllRecipients().stream().map(r -> {
            RecipientForm recipient = new RecipientForm();
            recipient.setName(r.getName());
            recipient.setEmail(r.getEmail());
            return recipient;
        }).collect(Collectors.toList()));
        appForm.setGroupIds(appManager.getGroups().stream().map(g -> g.getName()).collect(Collectors.toList()));
        return "admin/apps/show";
    }

    @RequestMapping(value = "/admin/apps/add", method = RequestMethod.POST)
    public String add(@ModelAttribute AppForm appForm, RedirectAttributes redirectAttrs) {
        IApp app = new App();
        if (appForm.getGroupType() == null
                || appForm.getGroupType() == GroupType.NEW && appForm.getGroupId().trim().isEmpty()) {
            redirectAttrs.addFlashAttribute("show_alert", true);
            redirectAttrs.addFlashAttribute("alert_type", "danger");
            redirectAttrs.addFlashAttribute("alert_msg",
                    "App could not be Stored. Please create a group for this app or select from an existing one.");
            return "redirect:/admin/apps/add";
        }
        appHelper.copyAppInfo(app, appForm);
        appManager.addApp(app);
        redirectAttrs.addFlashAttribute("show_alert", true);
        redirectAttrs.addFlashAttribute("alert_type", "success");
        redirectAttrs.addFlashAttribute("alert_msg", "App was successfully added.");
        return "redirect:/";
    }

}
