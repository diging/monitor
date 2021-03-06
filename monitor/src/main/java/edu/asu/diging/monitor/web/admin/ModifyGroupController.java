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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.diging.monitor.core.exceptions.GroupNotFoundException;
import edu.asu.diging.monitor.core.model.impl.Group;
import edu.asu.diging.monitor.core.service.IAppManager;
import edu.asu.diging.monitor.core.service.IGroupManager;
import edu.asu.diging.monitor.core.service.impl.GroupValidator;
import edu.asu.diging.monitor.web.admin.forms.AppForm;
import edu.asu.diging.monitor.web.admin.forms.GroupForm;

@Controller
public class ModifyGroupController {

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

    @RequestMapping(value = "/admin/groups/{id}/modify", method = RequestMethod.GET)
    public String show(Model model, @PathVariable("id") String id, RedirectAttributes redirectAttrs) {
        Group group = null;
        try {
            group = groupManager.getGroup(id);
        } catch (GroupNotFoundException e) {
            logger.error("Could not modify group", e);
            redirectAttrs.addFlashAttribute("show_alert", true);
            redirectAttrs.addFlashAttribute("alert_type", "danger");
            redirectAttrs.addFlashAttribute("alert_msg", "The selected group doesn't exist.");
            return "redirect:/admin/groups/show";
        }

        GroupForm groupForm = new GroupForm();
        groupForm.setId(id);
        groupForm.setName(group.getName());
        model.addAttribute("groupForm", groupForm);
        model.addAttribute("appIds", group.getApps().stream().map(a -> a.getId()).collect(Collectors.toList()));
        groupForm.setApps(appManager.getApps().stream().map(a -> {
            AppForm app = new AppForm();
            app.setId(a.getId());
            app.setName(a.getName());
            return app;
        }).collect(Collectors.toList()));

        return "admin/groups/add";
    }

    @RequestMapping(value = "/admin/groups/{id}/modify", method = RequestMethod.POST)
    public String modify(Model model, @ModelAttribute @Validated GroupForm groupForm, BindingResult result,
            RedirectAttributes redirectAttrs, @PathVariable("id") String id) {
        Group group = null;
        try {
            group = groupManager.getGroup(id);
        } catch (GroupNotFoundException e) {
            logger.error("Could not update group.", e);
            redirectAttrs.addFlashAttribute("show_alert", true);
            redirectAttrs.addFlashAttribute("alert_type", "danger");
            redirectAttrs.addFlashAttribute("alert_msg", "Group does not exist");
            return "redirect:/";
        }
        if (result.hasErrors()) {
            model.addAttribute("appIds", group.getApps().stream().map(a -> a.getId()).collect(Collectors.toList()));
            groupForm.setApps(appManager.getApps().stream().map(a -> {
                AppForm app = new AppForm();
                app.setId(a.getId());
                app.setName(a.getName());
                return app;
            }).collect(Collectors.toList()));

            return "admin/groups/add";
        }

        groupManager.updateGroup(group, groupForm);

        redirectAttrs.addFlashAttribute("show_alert", true);
        redirectAttrs.addFlashAttribute("alert_type", "success");
        redirectAttrs.addFlashAttribute("alert_msg", "Group was successfully updated.");
        return "redirect:/";
    }

}
