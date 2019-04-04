package org.softuni.productshop.config;

import org.softuni.productshop.domain.entity.UserRole;
import org.softuni.productshop.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DataBaseSeeder {

    private final UserRoleRepository userRoleRepository;

    @Autowired
    public DataBaseSeeder(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @PostConstruct
    public void seedRole() {
        if (this.userRoleRepository.count() == 0) {
            try {
                this.userRoleRepository.saveAndFlush(new UserRole("ROOT"));
                this.userRoleRepository.saveAndFlush(new UserRole("ADMIN"));
                this.userRoleRepository.saveAndFlush(new UserRole("MODERATOR"));
                this.userRoleRepository.saveAndFlush(new UserRole("USER"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
