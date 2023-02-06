package edu.asu.diging.monitor.web.admin;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.diging.monitor.core.model.INotificationRecipient;
import edu.asu.diging.monitor.core.service.IAppManager;
import edu.asu.diging.monitor.core.service.INotificationManager;
import edu.asu.diging.monitor.web.admin.forms.AppForm;
import edu.asu.diging.monitor.web.admin.forms.RecipientForm;

@Controller
public class ModifyRecipientController {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private INotificationManager manager;
    
    @Autowired
    private IAppManager appManager;
    
    
    
    @RequestMapping(value = "/admin/recipients/modify/{email}/", method = RequestMethod.GET)
    public String show(@PathVariable("email") String email, Model model) {
        INotificationRecipient recipientDetails = manager.getRecipient(email);
        RecipientForm recipientForm = new RecipientForm();
        recipientForm.setEmail(recipientDetails.getEmail());
        recipientForm.setName(recipientDetails.getName());
        // Set App IDs to the list of apps of the recipient
        recipientForm.setApps(appManager.getApps().stream().map(a -> {
            AppForm app = new AppForm();
            app.setId(a.getId());
            app.setName(a.getName());
            return app;
        }).collect(Collectors.toList()));
        model.addAttribute("recipientForm", recipientForm);
        return "admin/recipients/modify";
    }
}
