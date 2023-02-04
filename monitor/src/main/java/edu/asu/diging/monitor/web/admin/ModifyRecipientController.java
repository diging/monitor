package edu.asu.diging.monitor.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.diging.monitor.core.service.INotificationManager;

@Controller
public class ModifyRecipientController {
    
    @Autowired
    private INotificationManager manager;
    
    @RequestMapping(value = "/admin/recipients/{id}/modify", method = RequestMethod.POST)
    public String modifyRecipient(@PathVariable("id") String email) {
        //manager.modifyRecipient;
        return "redirect:/admin/recipient/list";
    }
}
