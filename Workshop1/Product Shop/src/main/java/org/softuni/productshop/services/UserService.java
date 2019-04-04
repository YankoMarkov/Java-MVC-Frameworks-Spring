package org.softuni.productshop.services;

import org.softuni.productshop.domain.models.services.UserRoleServiceModel;
import org.softuni.productshop.domain.models.services.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserServiceModel saveUser(UserServiceModel userService);

    UserServiceModel updateUsersRole(UserServiceModel userService, UserRoleServiceModel userRoleService);

    UserServiceModel updateUser(UserServiceModel userService, String oldPassword);

    UserServiceModel getUserById(String id);

    UserServiceModel getUserByUsername(String username);

    List<UserServiceModel> getAllUsers();
}
