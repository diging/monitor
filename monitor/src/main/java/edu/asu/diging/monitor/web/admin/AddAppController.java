package edu.asu.diging.monitor.web.admin;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.diging.monitor.core.model.IApp;
import edu.asu.diging.monitor.core.model.impl.App;
import edu.asu.diging.monitor.core.service.IAppHelper;
import edu.asu.diging.monitor.core.service.IAppManager;
import edu.asu.diging.monitor.core.service.INotificationManager;
import edu.asu.diging.monitor.web.admin.forms.AppForm;
import edu.asu.diging.monitor.web.admin.forms.RecipientForm;

@Controller
public class AddAppController {
	
	@Autowired
	private IAppManager appManager;
	
	@Autowired
	private IAppHelper appHelper;
	
	@Autowired
	private INotificationManager manager;

    @RequestMapping(value = "/admin/apps/add")
    public String show(Model model) {
        AppForm appForm = new AppForm();
        model.addAttribute("appForm", appForm);
        appForm.setRecipients(manager.getAllRecipients().stream().map(r -> {
            RecipientForm recipient = new RecipientForm();
            recipient.setName(r.getName());
            recipient.setEmail(r.getEmail());
            return recipient;
        }).collect(Collectors.toList()));
        return "admin/apps/show";
    }
	
	@RequestMapping(value="/admin/apps/add", method=RequestMethod.POST)
	public String add(@ModelAttribute AppForm appForm) {
		IApp app = new App();
		appHelper.copyAppInfo(app, appForm);
		appManager.addApp(app);
		return "redirect:/admin/apps/add";
	}
}
