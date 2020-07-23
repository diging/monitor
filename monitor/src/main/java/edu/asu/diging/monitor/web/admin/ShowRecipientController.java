package edu.asu.diging.monitor.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.diging.monitor.core.service.IAppManager;
import edu.asu.diging.monitor.core.service.INotificationManager;

@Controller
public class ShowRecipientController {
	
	@Autowired
	private INotificationManager manager;
	
	@Autowired
	private IAppManager appManager;
	
	@RequestMapping(value="/admin/recipient/list", method=RequestMethod.GET)
	public String showRecipients(Model model) {
		model.addAttribute("recipients", manager.getAllRecipients());
		model.addAttribute("appCount", appManager.getApps().size());
		return "admin/recipients/show";
	}

}
