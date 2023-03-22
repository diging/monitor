package edu.asu.diging.monitor.web.admin;

import java.util.Arrays;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.diging.monitor.core.exceptions.GroupNotFoundException;
import edu.asu.diging.monitor.core.exceptions.UnstorableObjectException;
import edu.asu.diging.monitor.core.model.IApp;
import edu.asu.diging.monitor.core.service.IAppHelper;
import edu.asu.diging.monitor.core.service.IAppManager;
import edu.asu.diging.monitor.core.service.impl.AppValidator;
import edu.asu.diging.monitor.web.admin.forms.AppForm;

@Controller
public class ModifyAppController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IAppManager appManager;

    @Autowired
    private IAppHelper appHelper;

    @Autowired
    private AppValidator appValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(appValidator);
    }

    @RequestMapping(value = "/admin/apps/{id}/modify", method = RequestMethod.GET)
    public String show(Model model, @PathVariable("id") String id) {
        IApp app = appManager.getApp(id);
        AppForm appForm = new AppForm();
        model.addAttribute("appForm", appForm);
        appHelper.copyAppInfoToForm(app, appForm);
        model.addAttribute("appRecipients",
                app.getRecipients().stream().map(x -> x.getEmail()).collect(Collectors.toList()));
        return "admin/apps/show";
    }

    @RequestMapping(value = "/admin/apps/{id}/modify", method = RequestMethod.POST)
    public String update(Model model, @ModelAttribute("appForm") @Validated AppForm appForm, BindingResult result,
            @PathVariable("id") String id, RedirectAttributes redirectAttrs) {
        IApp app = appManager.getApp(id);
        if (result.hasErrors()) {
            appHelper.copyGroupAndRecipientInfoToForm(appForm);
            model.addAttribute("appRecipients",
                    app.getRecipients().stream().map(x -> x.getEmail()).collect(Collectors.toList()));
            return "admin/apps/show";
        }
        try {
            if (!appForm.getTagString().isBlank()) {
                String[] tagStrings = appForm.getTagString().split(",");
                appForm.setTags(Arrays.stream(tagStrings).map(tag -> tag.trim()).collect(Collectors.toList()));
            }
            appManager.updateApp(app, appForm);
        } catch (GroupNotFoundException e) {
            logger.error("Could not find Group", e);
            redirectAttrs.addFlashAttribute("show_alert", true);
            redirectAttrs.addFlashAttribute("alert_type", "danger");
            redirectAttrs.addFlashAttribute("alert_msg", "App update failed. The selected group does not exist.");
            return "redirect:/";
        } catch (UnstorableObjectException e) {
            logger.error("Could not store Group", e);
            redirectAttrs.addFlashAttribute("show_alert", true);
            redirectAttrs.addFlashAttribute("alert_type", "danger");
            redirectAttrs.addFlashAttribute("alert_msg", "App update failed. New group couldn't be stored ");
            return "redirect:/";
        }
        redirectAttrs.addFlashAttribute("show_alert", true);
        redirectAttrs.addFlashAttribute("alert_type", "success");
        redirectAttrs.addFlashAttribute("alert_msg", "App was successfully updated.");
        return "redirect:/";
    }
}
