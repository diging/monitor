package edu.asu.diging.monitor.web.admin;

import java.net.HttpURLConnection;
import java.net.URL;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PingAppController {

	@RequestMapping(value = "/admin/apps/ping", method = RequestMethod.POST)
	public String getStatus(@RequestParam("url") String url, RedirectAttributes redirectAttrs) {
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(3000);
			connection.connect();
			int code = connection.getResponseCode();
			if (code == 200) {
				redirectAttrs.addFlashAttribute("show_alert", true);
				redirectAttrs.addFlashAttribute("alert_type", "success");
				redirectAttrs.addFlashAttribute("alert_msg", "Ping successful");
			}
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute("show_alert", true);
			redirectAttrs.addFlashAttribute("alert_type", "danger");
			redirectAttrs.addFlashAttribute("alert_msg", "Ping failure. App is unreachable");
		}
		return "redirect:/";
	}
}
