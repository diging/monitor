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

import edu.asu.diging.monitor.core.exceptions.UnstorableObjectException;
import edu.asu.diging.monitor.core.model.impl.Group;
import edu.asu.diging.monitor.core.service.IAppManager;
import edu.asu.diging.monitor.core.service.IGroupManager;
import edu.asu.diging.monitor.core.service.impl.GroupValidator;
import edu.asu.diging.monitor.web.admin.forms.AppForm;
import edu.asu.diging.monitor.web.admin.forms.GroupForm;

@Controller
public class AddGroupController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IAppManager appManager;

    @Autowired
    private IGroupManager groupManager;

    @Autowired
    private GroupValidator groupValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(groupValidator);
    }

    @RequestMapping(value = "/admin/groups/add", method = RequestMethod.GET)
    public String showAddGroup(Model model) {
        GroupForm groupForm = new GroupForm();
        model.addAttribute("groupForm", groupForm);
        groupForm.setApps(appManager.getApps().stream().map(a -> {
            AppForm app = new AppForm();
            app.setId(a.getId());
            app.setName(a.getName());
            return app;
        }).collect(Collectors.toList()));
        return "admin/groups/add";
    }

    @RequestMapping(value = "/admin/groups/add", method = RequestMethod.POST)
    public String add(@ModelAttribute @Validated GroupForm groupForm, BindingResult result,
            RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            groupForm.setApps(appManager.getApps().stream().map(a -> {
                AppForm app = new AppForm();
                app.setId(a.getId());
                app.setName(a.getName());
                return app;
            }).collect(Collectors.toList()));
            return "admin/groups/add";

        }
        try {

            Group group = groupManager.createGroup(groupForm.getName());
            if (groupForm.getAppIds() != null)
                groupForm.getAppIds().stream().map(id -> appManager.getApp(id)).forEach(a -> {
                    a.setGroup(group);
                    appManager.updateApp(a);
                });
        } catch (UnstorableObjectException e) {
            logger.error("Could not store group.", e);
            redirectAttrs.addFlashAttribute("show_alert", true);
            redirectAttrs.addFlashAttribute("alert_type", "danger");
            redirectAttrs.addFlashAttribute("alert_msg", "Group could not be created.");
            return "redirect:/admin/groups/show";
        }
        redirectAttrs.addFlashAttribute("show_alert", true);
        redirectAttrs.addFlashAttribute("alert_type", "success");
        redirectAttrs.addFlashAttribute("alert_msg", "Group was successfully added.");
        return "redirect:/admin/groups/show";
    }
}
