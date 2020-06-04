package edu.asu.diging.monitor.web.admin;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.diging.monitor.core.model.IApp;
import edu.asu.diging.monitor.core.service.IAppHelper;
import edu.asu.diging.monitor.core.service.IAppManager;
import edu.asu.diging.monitor.web.admin.forms.AppForm;

@Controller
public class ModifyAppController {

    @Autowired
    private IAppManager appManager;

    @Autowired
    private IAppHelper appHelper;

    @Autowired
    BCryptPasswordEncoder bcryptDecoder;

    @RequestMapping(value = "/admin/apps/{id}/modify", method = RequestMethod.GET)
    public String show(String username, String password, Model model, @PathVariable("id") String id,
            RedirectAttributes redirectAttrs) {
        IApp app = appManager.getApp(id);
        if (bcryptDecoder.matches(password, app.getUser().getPassword())
                && username.equals(app.getUser().getUsername())) {

            AppForm appForm = new AppForm();

            appHelper.copyAppInfoToForm(app, appForm);
            model.addAttribute("appForm", appForm);
            model.addAttribute("appRecipients",
                    app.getRecipients().stream().map(x -> x.getEmail()).collect(Collectors.toList()));
            return "admin/apps/show";
        }

        redirectAttrs.addFlashAttribute("show_alert", true);
        redirectAttrs.addFlashAttribute("alert_type", "danger");
        redirectAttrs.addFlashAttribute("alert_msg", "Cannot modify. Credentials invalid");
        return "redirect:/";
    }

    @RequestMapping(value = "/admin/apps/{id}/modify", method = RequestMethod.POST)
    public String update(@ModelAttribute AppForm appForm, @PathVariable("id") String id,
            RedirectAttributes redirectAttrs) {
        IApp app = appManager.getApp(id);
        if (app.getRecipients() != null) {
            appManager.deleteExistingRecipients(app);
        }
        app = appHelper.copyAppInfo(appManager.getApp(id), appForm);
        appManager.updateApp(app);
        redirectAttrs.addFlashAttribute("show_alert", true);
        redirectAttrs.addFlashAttribute("alert_type", "success");
        redirectAttrs.addFlashAttribute("alert_msg", "App was successfully updated.");
        return "redirect:/";
    }
}
