package edu.asu.diging.monitor.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
    		model.addAttribute("apps", appManager.getApps());
    		model.addAttribute("recipientCount", manager.getAllRecipients().size());
        return "home";
    }
}
