package edu.asu.diging.monitor.web.admin;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.diging.monitor.core.exceptions.GroupNotFoundException;
import edu.asu.diging.monitor.core.exceptions.UnstorableObjectException;
import edu.asu.diging.monitor.core.service.IAppManager;
import edu.asu.diging.monitor.core.service.IGroupManager;
import edu.asu.diging.monitor.core.service.INotificationManager;
import edu.asu.diging.monitor.core.service.impl.AppValidator;
import edu.asu.diging.monitor.web.admin.forms.AppForm;
import edu.asu.diging.monitor.web.admin.forms.RecipientForm;

@Controller
public class AddAppController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IAppManager appManager;

    @Autowired
    private AppValidator appValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(appValidator);
    }

    @Autowired
    private INotificationManager manager;

    @Autowired
    private IGroupManager groupManager;

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
        appForm.setGroupIds(groupManager.getGroups().stream()
                .collect(Collectors.toMap(g -> g.getId(), g -> g.getName())).entrySet());
        return "admin/apps/show";
    }

    @RequestMapping(value = "/admin/apps/add", method = RequestMethod.POST)
    public String add(@ModelAttribute @Validated AppForm appForm, BindingResult result,
            RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            appForm.setRecipients(manager.getAllRecipients().stream().map(r -> {
                RecipientForm recipient = new RecipientForm();
                recipient.setName(r.getName());
                recipient.setEmail(r.getEmail());
                return recipient;
            }).collect(Collectors.toList()));
            appForm.setGroupIds(groupManager.getGroups().stream()
                    .collect(Collectors.toMap(g -> g.getId(), g -> g.getName())).entrySet());
            return "admin/apps/show";
        }
        try {
            appManager.addApp(appForm);
        } catch (GroupNotFoundException e) {
            logger.error("Could not find Group", e);
            redirectAttrs.addFlashAttribute("show_alert", true);
            redirectAttrs.addFlashAttribute("alert_type", "danger");
            redirectAttrs.addFlashAttribute("alert_msg", "App creation failed. The selected group does not exist.");
            return "redirect:/";
        } catch (UnstorableObjectException e) {
            logger.error("Could not store Group", e);
            redirectAttrs.addFlashAttribute("show_alert", true);
            redirectAttrs.addFlashAttribute("alert_type", "danger");
            redirectAttrs.addFlashAttribute("alert_msg", "App creation failed. New group couldn't be stored.");
            return "redirect:/";
        }

        redirectAttrs.addFlashAttribute("show_alert", true);
        redirectAttrs.addFlashAttribute("alert_type", "success");
        redirectAttrs.addFlashAttribute("alert_msg", "App was successfully added.");
        return "redirect:/";
    }

}
