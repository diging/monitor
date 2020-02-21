package edu.asu.diging.monitor.web.admin;

import java.util.ArrayList;
import java.util.List;

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
	
	@RequestMapping(value="/admin/recipients/show", method=RequestMethod.GET)
	public String showRecipients(Model model) {
		List<RecipientForm> recipientList = new ArrayList<>();
		manager.showRecipients().entrySet().forEach( x-> {
			RecipientForm recipientForm = new RecipientForm();
			recipientForm.setName(x.getKey());
			recipientForm.setEmail(x.getValue());
			recipientList.add(recipientForm);
		});
		model.addAttribute("rcpts", recipientList);
		return "admin/recipients/show";
	}
	
	@RequestMapping(value="/admin/recipients/add", method=RequestMethod.GET)
	public String show(Model model) {
		model.addAttribute("recipientForm", new RecipientForm());
		return "admin/recipients/showAddPage";
	}
	
	@RequestMapping(value="/admin/recipients/add", method=RequestMethod.POST)
	public String add(@ModelAttribute RecipientForm recipientForm, RedirectAttributes redirectAttrs) {
		if (recipientForm.getEmail() == null || recipientForm.getEmail().trim().isEmpty()) {
			redirectAttrs.addFlashAttribute("show_alert", true);
			redirectAttrs.addFlashAttribute("alert_type", "danger");
			redirectAttrs.addFlashAttribute("alert_msg", "Recipient could not be stored. Please provide an email address.");
		} else {
			try {
				manager.addRecipient(recipientForm.getName(), recipientForm.getEmail());
				redirectAttrs.addFlashAttribute("show_alert", true);
				redirectAttrs.addFlashAttribute("alert_type", "success");
				redirectAttrs.addFlashAttribute("alert_msg", "Recipient was successfully registered.");
			} catch (EmailAlreadyRegisteredException e) {
				redirectAttrs.addFlashAttribute("show_alert", true);
				redirectAttrs.addFlashAttribute("alert_type", "danger");
				redirectAttrs.addFlashAttribute("alert_msg", "Recipient could not be stored. Email address already registered.");
				logger.error("Could not store recipient.", e);
			}
		}
		
		return "redirect:/admin/recipients/add";
	}
}
