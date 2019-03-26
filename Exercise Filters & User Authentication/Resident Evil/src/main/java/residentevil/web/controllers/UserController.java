package residentevil.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import residentevil.models.bindings.UserLoginBindingModel;
import residentevil.models.bindings.UserRegisterBindingModel;
import residentevil.models.services.UserRoleServiceModel;
import residentevil.models.services.UserServiceModel;
import residentevil.models.views.RoleViewModel;
import residentevil.models.views.UsersViewModel;
import residentevil.services.UserRoleService;
import residentevil.services.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {
	
	private final UserService userService;
	private final UserRoleService userRoleService;
	private final ModelMapper modelMapper;
	
	@Autowired
	public UserController(UserService userService, UserRoleService userRoleService, ModelMapper modelMapper) {
		this.userService = userService;
		this.userRoleService = userRoleService;
		this.modelMapper = modelMapper;
	}
	
	@GetMapping("/register")
	@PreAuthorize("isAnonymous()")
	public ModelAndView userRegister(@ModelAttribute("userRegister") UserRegisterBindingModel userRegister) {
		return this.view("register");
	}
	
	@PostMapping("/register")
	public ModelAndView userRegisterConfirm(@Valid @ModelAttribute("userRegister") UserRegisterBindingModel userRegister,
	                                        BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return this.view("register");
		}
		if (!userRegister.getPassword().equals(userRegister.getConfirmPassword())) {
			return this.view("register");
		}
		UserServiceModel userServiceModel = this.modelMapper.map(userRegister, UserServiceModel.class);
		if (this.userService.saveUser(userServiceModel) == null) {
			return this.view("register");
		}
		return this.redirect("/users/login");
	}
	
	@GetMapping("/login")
	@PreAuthorize("isAnonymous()")
	public ModelAndView userLogin(@ModelAttribute("userLogin") UserLoginBindingModel userLogin) {
		return this.view("login");
	}
	
	@GetMapping("/all")
	public ModelAndView usersAll(@ModelAttribute("allUsers") UsersViewModel allUsers,
	                             ModelAndView modelAndView) {
		List<UserServiceModel> userServiceModels = this.userService.getAllUsers();
		Set<UsersViewModel> usersViewModels = userServiceModels.stream()
				.map(user -> {
					UsersViewModel usersViewModel = this.modelMapper.map(user, UsersViewModel.class);
					Set<String> authorities = user.getAuthorities().stream()
							.map(UserRoleServiceModel::getAuthority)
							.collect(Collectors.toSet());
					usersViewModel.setAuthorities(authorities);
					return usersViewModel;
				})
				.collect(Collectors.toSet());
		
		modelAndView.addObject("allUsers", usersViewModels);
		return this.view("allUsers", modelAndView);
	}
	
	@PostMapping("changeRole")
	public ModelAndView changeRole(@RequestParam("id") String id,
	                               @Valid @ModelAttribute("role") RoleViewModel role,
	                               BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return this.view("allUsers");
		}
		UserServiceModel userServiceModel = this.userService.getUserById(id);
		if (userServiceModel.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ADMIN"))) {
			return this.redirect("/users/all");
		}
		UserRoleServiceModel userRoleServiceModel = this.userRoleService.getRoleByName(role.getAuthority());
		if (userServiceModel.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals(role.getAuthority()))) {
			return this.redirect("/users/all");
		}
		userServiceModel.getAuthorities().add(userRoleServiceModel);
		this.userService.updateUsersRole(userServiceModel, userRoleServiceModel);
		return this.redirect("/users/all");
	}
}
