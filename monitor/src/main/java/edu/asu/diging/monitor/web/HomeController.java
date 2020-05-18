package edu.asu.diging.monitor.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.asu.diging.monitor.core.model.IApp;
import edu.asu.diging.monitor.core.service.IAppManager;
import edu.asu.diging.monitor.core.service.INotificationManager;

@Controller
public class HomeController {

    @Autowired
    private IAppManager appManager;

    @Autowired
    private INotificationManager manager;

    @RequestMapping(value = "/")
    public String home(Model model) {
        model.addAttribute("appGroups", appManager.getGroups());
        model.addAttribute("recipientCount", manager.getAllRecipients().size());
        return "home";
    }

    @RequestMapping(value = "/reload", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<IApp> refresh(Model model) {
        return appManager.getApps();
    }
}
