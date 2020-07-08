package edu.asu.diging.monitor.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.diging.monitor.core.model.IApp;
import edu.asu.diging.monitor.core.service.IAppManager;
import edu.asu.diging.monitor.core.service.impl.AppValidator;
import edu.asu.diging.monitor.web.admin.forms.AppForm;

@Controller
public class ModifyAuthenticationController {

    @Autowired
    private IAppManager appManager;

    @Autowired
    private AppValidator appValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(appValidator);
    }

    @RequestMapping(value = "/admin/apps/{id}/modifyAuth", method = RequestMethod.GET)
    public String show(Model model, @PathVariable("id") String id) {
        IApp app = appManager.getApp(id);
        AppForm appForm = new AppForm();
        model.addAttribute("appForm", appForm);
        appForm.setUsername(app.getUsername());
        appForm.setId(app.getId());
        return "admin/apps/auth";
    }

    @RequestMapping(value = "/admin/apps/{id}/modifyAuth", method = RequestMethod.POST)
    public String updateAuth(Model model, @PathVariable("id") String id,
            @ModelAttribute("appForm") @Validated AppForm appForm, BindingResult result,
            RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            return "admin/apps/auth";
        }
        IApp app = appManager.getApp(id);
        appManager.updateAppAuth(appForm, app);
        redirectAttrs.addFlashAttribute("show_alert", true);
        redirectAttrs.addFlashAttribute("alert_type", "success");
        redirectAttrs.addFlashAttribute("alert_msg", "Authentication info was successfully updated.");
        return "redirect:/admin/apps/{id}/modify";
    }

}
