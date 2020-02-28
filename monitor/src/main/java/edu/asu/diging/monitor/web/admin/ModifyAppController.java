package edu.asu.diging.monitor.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.diging.monitor.core.model.IApp;
import edu.asu.diging.monitor.core.service.IAppManager;
import edu.asu.diging.monitor.web.admin.forms.AppForm;

public class ModifyAppController {
	
	
	@Autowired
	private IAppManager appManager;

	@RequestMapping(value="/admin/apps/modify")
	public String show(Model model) {
		model.addAttribute("appForm", new AppForm());
		return "admin/apps/modify";
	}
	
	@RequestMapping(value="/admin/apps/add", method=RequestMethod.POST)
	public String update(@ModelAttribute AppForm appForm) {
		IApp app = appManager.getApp(appForm.getId());
		app.setDescription(appForm.getDescription());
		app.setExpectedReturnCodes(appForm.getExpectedReturnCodes());
		app.setHealthUrl(appForm.getHealthUrl());
		app.setName(appForm.getName());
		app.setRetries(appForm.getRetries());
		app.setTimeout(appForm.getTimeout());
		app.setWarningReturnCodes(appForm.getWarningReturnCodes());
		app.setPingInterval(appForm.getPingInterval());
		app.setMethod(appForm.getMethod());
		appManager.updateApp(app);
		return "redirect:/admin/apps/add";
	}
	

}
