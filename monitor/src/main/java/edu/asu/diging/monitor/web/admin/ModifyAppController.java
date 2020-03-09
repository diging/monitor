package edu.asu.diging.monitor.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.diging.monitor.core.model.IApp;
import edu.asu.diging.monitor.core.model.IAppHelper;
import edu.asu.diging.monitor.core.service.IAppManager;
import edu.asu.diging.monitor.web.admin.forms.AppForm;

@Controller
public class ModifyAppController {
	
	
	@Autowired
	private IAppManager appManager;
	
	@Autowired
	private IAppHelper appHelper;

	
	@RequestMapping(value="/admin/apps/{id}/modify", method= RequestMethod.GET)
	public String show(Model model, @PathVariable("id") String id) {
		/* AppForm and App classes use the same properties.
		 * Validate UI form elements if modifying AppForm or App definition
		 */
		model.addAttribute("appForm", appManager.getApp(id));
		return "admin/apps/show";
	}
	
	@RequestMapping(value="/admin/apps/{id}/modify", method=RequestMethod.POST)
	public String update(@ModelAttribute AppForm appForm, @PathVariable("id") String id, RedirectAttributes redirectAttrs) {
		IApp app = appManager.getApp(id);
		appHelper.copyAppInfo(app, appForm);
		appManager.updateApp(app);
		redirectAttrs.addFlashAttribute("show_alert", true);
		redirectAttrs.addFlashAttribute("alert_type", "success");
		redirectAttrs.addFlashAttribute("alert_msg", "App was successfully updated.");
		return "redirect:/";
	}
}
