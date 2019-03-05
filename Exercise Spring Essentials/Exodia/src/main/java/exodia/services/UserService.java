package exodia.services;

import exodia.domain.models.services.UserServiceModel;

public interface UserService {
	
	boolean saveUser(UserServiceModel userService);
	
	UserServiceModel getUserByUsername(String username);
	
	boolean userExist(String username);
	
	boolean isValidUser(String username, String password);
}
