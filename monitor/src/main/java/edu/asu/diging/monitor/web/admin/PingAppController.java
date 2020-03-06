package edu.asu.diging.monitor.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.diging.monitor.core.cron.IAppChecker;
import edu.asu.diging.monitor.core.model.AppStatus;
import edu.asu.diging.monitor.core.model.IApp;
import edu.asu.diging.monitor.core.service.IAppManager;

@Controller
public class PingAppController {

	@Autowired
	private IAppManager manager;

	@Autowired
	private IAppChecker checker;

	@RequestMapping(value = "/admin/apps/{id}/ping", method = RequestMethod.POST)
	public String getStatus(@PathVariable("id") String id, RedirectAttributes redirectAttrs) {
		IApp app = manager.getApp(id);
		checker.checkAppNow(app);
		AppStatus appStatus = app.getLastAppTest().getStatus();
		if (AppStatus.OK == appStatus) {
			redirectAttrs.addFlashAttribute("show_alert", true);
			redirectAttrs.addFlashAttribute("alert_type", "success");
			redirectAttrs.addFlashAttribute("alert_msg", "Ping successful");
		} else {
			redirectAttrs.addFlashAttribute("show_alert", true);
			redirectAttrs.addFlashAttribute("alert_type", "danger");
			redirectAttrs.addFlashAttribute("alert_msg", "Ping failure. App is unreachable");
		}
		return "redirect:/";
	}
}
