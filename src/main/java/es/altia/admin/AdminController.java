package es.altia.admin;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

	@Autowired
	private IAdminService adminService;

	@GetMapping("/admin")
	public String adminPage(final Principal principal, final Model model) {
		model.addAttribute("greet", this.adminService.greetAdmin());
		return "admin/admin";
	}
}
