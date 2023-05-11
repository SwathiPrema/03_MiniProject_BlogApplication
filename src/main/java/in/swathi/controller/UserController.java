package in.swathi.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.swathi.binding.LoginForm;
import in.swathi.binding.RegisterForm;
import in.swathi.entity.UserDetails;
import in.swathi.service.BlogService;

@Controller
public class UserController {

	@Autowired
	private BlogService blogService;

	@Autowired
	private HttpSession session;
	

	@GetMapping("/login")
	public String loginPage(@ModelAttribute("login") LoginForm login) {
		return "login";
	}

	@PostMapping("/userLogin")
	public String userLoginPage(@ModelAttribute("login") LoginForm login, Model model) {

		boolean status = blogService.userLogin(login);

		if (status) {
			return "redirect:/postindex";
		} else {
			model.addAttribute("fail", "Invalid Credintials");
		}

		return "login";
	}

	@GetMapping("/register")
	public String registerPage(@ModelAttribute("form") RegisterForm form) {
		return "register";
	}

	@PostMapping("/userRegister")
	public String userRegisterPage(@ModelAttribute("form") RegisterForm form, Model model) {

		boolean userDetails = blogService.saveUserDetails(form);

		if (userDetails) {

			model.addAttribute("saved", "Your registration is successfully completed");

		} else {

			model.addAttribute("notsaved", "Your entered a duplicate email");
		}

		return "register";
	}

	@GetMapping("/userLogout")
	public String logoutPage() {

		session.invalidate();

		return "index";
	}
	
	
	
}
