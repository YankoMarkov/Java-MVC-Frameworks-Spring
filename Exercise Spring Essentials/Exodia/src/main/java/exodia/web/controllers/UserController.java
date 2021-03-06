package exodia.web.controllers;

import exodia.domain.models.bindings.UserLoginBindingModel;
import exodia.domain.models.bindings.UserRegisterBindingModel;
import exodia.domain.models.services.UserServiceModel;
import exodia.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
public class UserController extends BaseController {
	
	private final UserService userService;
	private final ModelMapper modelMapper;
	
	@Autowired
	public UserController(UserService userService, ModelMapper modelMapper) {
		this.userService = userService;
		this.modelMapper = modelMapper;
	}
	
	@GetMapping("/register")
	public ModelAndView register(Model model) {
		if (model.asMap().isEmpty()) {
			model.addAttribute("registerModel", new UserRegisterBindingModel());
		}
		return this.view("register");
	}
	
	@PostMapping("register")
	public ModelAndView registerConfirm(@ModelAttribute @Valid UserRegisterBindingModel userRegister,
	                                    BindingResult bindingResult,
	                                    RedirectAttributes redirectAttributes) {
		
		UserServiceModel userServiceModel = this.modelMapper.map(userRegister, UserServiceModel.class);
		if (bindingResult.hasErrors() ||
				this.userService.userExist(userRegister.getUsername()) ||
				!userRegister.getPassword().equals(userRegister.getConfirmPassword()) ||
				!this.userService.saveUser(userServiceModel)) {
			
			redirectAttributes.addFlashAttribute("registerModel", userRegister);
			redirectAttributes.addFlashAttribute("errors", bindingResult.getFieldErrors().stream()
					.map(error -> error.getDefaultMessage()).collect(Collectors.toList()));
			return this.redirect("/register");
		}
		return this.redirect("/login");
	}
	
	@GetMapping("/login")
	public ModelAndView login(Model model) {
		if (model.asMap().isEmpty()) {
			model.addAttribute("loginModel", new UserLoginBindingModel());
		}
		return this.view("login");
	}
	
	@PostMapping("/login")
	public ModelAndView loginConfirm(@ModelAttribute @Valid UserLoginBindingModel userLogin,
	                                 BindingResult bindingResult,
	                                 RedirectAttributes redirectAttributes,
	                                 HttpSession session) {
		
		if (bindingResult.hasErrors() ||
				!this.userService.isValidUser(userLogin.getUsername(), userLogin.getPassword())) {
			
			redirectAttributes.addFlashAttribute("loginModel", userLogin);
			redirectAttributes.addFlashAttribute("errors", bindingResult.getFieldErrors().stream()
					.map(error -> error.getDefaultMessage()).collect(Collectors.toList()));
			return this.redirect("/login");
		}
		session.setAttribute("username", userLogin.getUsername());
		return this.redirect("/home");
	}
	
	@GetMapping("/logout")
	public ModelAndView logout(HttpSession session) {
		if (session.getAttribute("username") == null) {
			this.redirect("/login");
		}
		session.invalidate();
		return this.redirect("/");
	}
}
