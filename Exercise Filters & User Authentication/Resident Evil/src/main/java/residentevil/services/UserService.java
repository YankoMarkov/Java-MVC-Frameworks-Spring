package residentevil.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import residentevil.models.services.UserRoleServiceModel;
import residentevil.models.services.UserServiceModel;

import java.util.List;

public interface UserService extends UserDetailsService {
	
	UserServiceModel saveUser(UserServiceModel userService);
	
	UserServiceModel updateUsersRole(UserServiceModel userService, UserRoleServiceModel userRoleService);
	
	UserServiceModel getUserById(String id);
	
	UserServiceModel getUserByUsername(String username);
	
	List<UserServiceModel> getAllUsers();
}
