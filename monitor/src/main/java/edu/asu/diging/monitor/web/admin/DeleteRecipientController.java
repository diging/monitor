package edu.asu.diging.monitor.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.diging.monitor.core.service.INotificationManager;

@Controller
public class DeleteRecipientController {

	@Autowired
	private INotificationManager manager;

	@RequestMapping(value = "/admin/recipients/{id}/delete", method = RequestMethod.POST)
	public String deleteApp(@PathVariable("id") String email) {
		manager.deleteRecipient(email);
		return "redirect:/admin/recipient/list";
	}
}