package org.softuni.productshop.services;

import org.modelmapper.ModelMapper;
import org.softuni.productshop.domain.entity.UserRole;
import org.softuni.productshop.domain.models.services.UserRoleServiceModel;
import org.softuni.productshop.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserRoleServiceImpl(UserRoleRepository userRoleRepository, ModelMapper modelMapper) {
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserRoleServiceModel getRoleByName(String name) {
        UserRole role = this.userRoleRepository.findByAuthority(name).orElse(null);
        if (role == null) {
            return null;
        }
        return this.modelMapper.map(role, UserRoleServiceModel.class);
    }

    @Override
    public Set<UserRoleServiceModel> getAllRoles() {
        List<UserRole> roles = this.userRoleRepository.findAll();
        if (roles == null) {
            return new HashSet<>();
        }
        return roles.stream()
                .map(role -> this.modelMapper.map(role, UserRoleServiceModel.class))
                .collect(Collectors.toUnmodifiableSet());
    }
}