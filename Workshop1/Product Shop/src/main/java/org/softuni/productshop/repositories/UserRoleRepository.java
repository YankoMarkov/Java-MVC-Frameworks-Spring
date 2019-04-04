package org.softuni.productshop.repositories;

import org.softuni.productshop.domain.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {

    Optional<UserRole> findByAuthority(String authority);
}
