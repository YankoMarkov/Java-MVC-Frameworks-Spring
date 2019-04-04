package org.softuni.productshop.services;

import org.softuni.productshop.domain.models.services.UserRoleServiceModel;

import java.util.Set;

public interface UserRoleService {

    UserRoleServiceModel getRoleByName(String name);

    Set<UserRoleServiceModel> getAllRoles();
}