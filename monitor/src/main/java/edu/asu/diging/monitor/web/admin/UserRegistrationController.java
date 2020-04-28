package edu.asu.diging.monitor.web.admin;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.diging.monitor.core.exceptions.UserAlreadyExistsException;
import edu.asu.diging.monitor.core.service.IUserService;
import edu.asu.diging.monitor.web.admin.forms.UserForm;

@Controller
public class UserRegistrationController {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IUserService userService;

	@RequestMapping(value = "/admin/register")
	public String show(Model model) {
		model.addAttribute("userForm", new UserForm());
		return "admin/register";
	}

	@RequestMapping(value = "/admin/register/add", method = RequestMethod.POST)
	public String add(@ModelAttribute @Valid UserForm userForm, BindingResult bindingResult,
			RedirectAttributes redirectAttrs) {
		if (bindingResult.getErrorCount() != 0) {
			redirectAttrs.addFlashAttribute("show_alert", true);
			redirectAttrs.addFlashAttribute("alert_type", "danger");
			redirectAttrs.addFlashAttribute("alert_msg", "Cannot leave blank");
		} else {
			try {
				userService.registerNewUserAccount(userForm);
				redirectAttrs.addFlashAttribute("show_alert", true);
				redirectAttrs.addFlashAttribute("alert_type", "success");
				redirectAttrs.addFlashAttribute("alert_msg", "User account was successfully registered.");
			} catch (UserAlreadyExistsException e) {
				redirectAttrs.addFlashAttribute("show_alert", true);
				redirectAttrs.addFlashAttribute("alert_type", "danger");
				redirectAttrs.addFlashAttribute("alert_msg", "User could not be added. Username already exists.");
				logger.error("Could not create new user", e);
			}
		}
		return "redirect:/admin/register";
	}
}
