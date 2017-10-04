package edu.asu.diging.monitor.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.diging.monitor.core.service.IAppManager;

@Controller
public class DeleteAppController {

	@Autowired
	private IAppManager appManager;
	
	@RequestMapping(value="/admin/apps/{id}/delete", method=RequestMethod.POST)
	public String deleteApp(@PathVariable("id") String id) {
		appManager.deleteApp(id);
		
		return "redirect:/";
	}
}
