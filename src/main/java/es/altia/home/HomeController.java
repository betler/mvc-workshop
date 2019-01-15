package es.altia.home;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.SpringVersion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
class HomeController {

	@Value("${workshop.name}")
	private String workshopName;

	@ModelAttribute("module")
	String module() {
		return "home";
	}

	@GetMapping("/")
	String index(final Principal principal, final Model model) {
		model.addAttribute("workshopName", this.workshopName);
		model.addAttribute("springVersion", SpringVersion.getVersion());
		return principal != null ? "home/homeSignedIn" : "home/homeNotSignedIn";
	}
}
