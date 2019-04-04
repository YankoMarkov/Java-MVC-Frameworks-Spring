package org.softuni.productshop.repositories;

import org.softuni.productshop.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM org.softuni.productshop.domain.entity.User u ORDER BY u.username")
    List<User> findAllOrderByUsername();
}
