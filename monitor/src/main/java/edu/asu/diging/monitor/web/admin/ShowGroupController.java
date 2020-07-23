package edu.asu.diging.monitor.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.diging.monitor.core.service.IAppManager;
import edu.asu.diging.monitor.core.service.IGroupManager;

@Controller
public class ShowGroupController {

    @Autowired
    private IAppManager appManager;

    @Autowired
    private IGroupManager groupManager;

    @RequestMapping(value = "/admin/groups/show", method = RequestMethod.GET)
    public String showGroups(Model model) {
        model.addAttribute("groups", groupManager.getGroups());
        model.addAttribute("appCount", appManager.getApps().size());
        return "admin/groups/show";

    }

}
