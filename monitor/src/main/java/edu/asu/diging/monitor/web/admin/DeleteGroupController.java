package edu.asu.diging.monitor.web.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.diging.monitor.core.exceptions.GroupNotFoundException;
import edu.asu.diging.monitor.core.model.impl.Group;
import edu.asu.diging.monitor.core.service.IGroupManager;

@Controller
public class DeleteGroupController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IGroupManager groupManager;

    @RequestMapping(value = "/admin/groups/{id}/delete", method = RequestMethod.POST)
    public String delete(@PathVariable("id") String id, RedirectAttributes redirectAttrs) {
        Group group = null;
        try {
            group = groupManager.getGroup(id);
        } catch (GroupNotFoundException e) {
            logger.error("Group couldn't be deleted", e);
            redirectAttrs.addFlashAttribute("show_alert", true);
            redirectAttrs.addFlashAttribute("alert_type", "danger");
            redirectAttrs.addFlashAttribute("alert_msg", "Group could not be deleted.");
            return "redirect:/admin/groups/show";
        }
        groupManager.deleteGroup(group);
        return "redirect:/admin/groups/show";
    }
}
