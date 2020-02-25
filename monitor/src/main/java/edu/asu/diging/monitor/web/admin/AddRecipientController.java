package edu.asu.diging.monitor.web.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.diging.monitor.core.exceptions.EmailAlreadyRegisteredException;
import edu.asu.diging.monitor.core.service.INotificationManager;
import edu.asu.diging.monitor.web.admin.forms.RecipientForm;

@Controller
public class AddRecipientController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private INotificationManager manager;
	
	
	@RequestMapping(value="/admin/recipients/add", method=RequestMethod.GET)
	public String show(Model model) {
		model.addAttribute("recipientForm", new RecipientForm());
		return "admin/recipients/add";
	}
	
	@RequestMapping(value="/admin/recipients/add", method=RequestMethod.POST)
	public String add(@ModelAttribute RecipientForm recipientForm, RedirectAttributes redirectAttrs) {
		if (recipientForm.getEmail() == null || recipientForm.getEmail().trim().isEmpty()) {
			redirectAttrs.addFlashAttribute("show_alert", true);
			redirectAttrs.addFlashAttribute("alert_type", "danger");
			redirectAttrs.addFlashAttribute("alert_msg", "Recipient could not be stored. Please provide an email address.");
			return "redirect:/admin/recipients/add";
		} else {
			try {
				manager.addRecipient(recipientForm.getName(), recipientForm.getEmail());
				redirectAttrs.addFlashAttribute("show_alert", true);
				redirectAttrs.addFlashAttribute("alert_type", "success");
				redirectAttrs.addFlashAttribute("alert_msg", "Recipient was successfully registered.");
				return "redirect:/admin/recipient/list";
			} catch (EmailAlreadyRegisteredException e) {
				redirectAttrs.addFlashAttribute("show_alert", true);
				redirectAttrs.addFlashAttribute("alert_type", "danger");
				redirectAttrs.addFlashAttribute("alert_msg", "Recipient could not be stored. Email address already registered.");
				logger.error("Could not store recipient.", e);
				return "redirect:/admin/recipients/add";
			}
		}
		
	}
}
