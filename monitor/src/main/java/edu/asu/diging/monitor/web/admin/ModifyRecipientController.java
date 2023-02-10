package edu.asu.diging.monitor.web.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import edu.asu.diging.monitor.core.model.INotificationRecipient;
import edu.asu.diging.monitor.core.service.IAppManager;
import edu.asu.diging.monitor.core.service.INotificationManager;
import edu.asu.diging.monitor.web.admin.forms.AppForm;
import edu.asu.diging.monitor.web.admin.forms.RecipientForm;

@Controller
public class ModifyRecipientController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private INotificationManager manager;

    @Autowired
    private IAppManager appManager;

    @RequestMapping(value = "/admin/recipients/{email}/modify", method = RequestMethod.GET)
    public String show(@PathVariable("email") String email, Model model) {
        INotificationRecipient recipientDetails = manager.getRecipient(email);
        RecipientForm recipientForm = new RecipientForm();
        recipientForm.setEmail(recipientDetails.getEmail());
        recipientForm.setName(recipientDetails.getName());
        recipientForm.setApps(appManager.getApps().stream().map(a -> {
            AppForm app = new AppForm();
            app.setId(a.getId());
            app.setName(a.getName());
            return app;
        }).collect(Collectors.toList()));

        List<String> appsList = recipientDetails.getApps().stream().map(a -> {
            return a.getId();
        }).collect(Collectors.toList());

        model.addAttribute("recipientForm", recipientForm);
        model.addAttribute("recipientApps", appsList);
        return "admin/recipients/modify";
    }

    @RequestMapping(value = "/admin/recipients/modify", method = RequestMethod.POST)
    public String modify(@ModelAttribute RecipientForm recipientForm, RedirectAttributes redirectAttrs) {
        if (recipientForm.getEmail() == null || recipientForm.getEmail().trim().isEmpty()) {
            redirectAttrs.addFlashAttribute("show_alert", true);
            redirectAttrs.addFlashAttribute("alert_type", "danger");
            redirectAttrs.addFlashAttribute("alert_msg",
                    "Recipient could not be stored. Please provide an email address.");
            return "redirect:/admin/recipients/modify";
        }
        if (recipientForm.getAppIds() == null) {
            recipientForm.setAppIds(new ArrayList<>());
        }
        manager.modifyRecipient(recipientForm.getEmail(), recipientForm.getName(), recipientForm.getAppIds());
        redirectAttrs.addFlashAttribute("show_alert", true);
        redirectAttrs.addFlashAttribute("alert_type", "success");
        redirectAttrs.addFlashAttribute("alert_msg", "Recipient was successfully registered.");
        return "redirect:/admin/recipient/list";
    }
}
