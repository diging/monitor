package edu.asu.diging.monitor.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.diging.monitor.core.model.IApp;
import edu.asu.diging.monitor.core.service.IAppManager;

@Controller
public class DeleteAppController {

    @Autowired
    private IAppManager appManager;

    @Autowired
    BCryptPasswordEncoder bcryptDecoder;

    @RequestMapping(value = "/admin/apps/{id}/delete", method = RequestMethod.POST)
    public String deleteApp(String username, String password, @PathVariable("id") String id,
            RedirectAttributes redirectAttrs) {
        IApp app = appManager.getApp(id);
        if (bcryptDecoder.matches(password, app.getUser().getPassword())
                && username.equals(app.getUser().getUsername())) {
            appManager.deleteApp(id);
            return "redirect:/";
        }
        redirectAttrs.addFlashAttribute("show_alert", true);
        redirectAttrs.addFlashAttribute("alert_type", "danger");
        redirectAttrs.addFlashAttribute("alert_msg", "Delete failed. Credentials invalid");
        return "redirect:/";
    }
}
