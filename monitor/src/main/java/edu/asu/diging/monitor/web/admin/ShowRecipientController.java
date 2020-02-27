package edu.asu.diging.monitor.web.admin;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.diging.monitor.core.model.INotificationRecipient;
import edu.asu.diging.monitor.core.service.INotificationManager;

@Controller
public class ShowRecipientController {
	
	@Autowired
	private INotificationManager manager;
	
	@RequestMapping(value="/admin/recipient/list", method=RequestMethod.GET)
	public String showRecipients(Model model) {
		List<INotificationRecipient> recipientList = manager.getAllRecipients();
		model.addAttribute("recipients", recipientList);
		return "admin/recipients/show";
	}

}
