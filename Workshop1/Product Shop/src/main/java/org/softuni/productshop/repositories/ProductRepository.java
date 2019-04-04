package org.softuni.productshop.repositories;

import org.softuni.productshop.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    Optional<Product> findByName(String name);

    @Query("SELECT p FROM org.softuni.productshop.domain.entity.Product p ORDER BY p.name")
    List<Product> findAllOrdered();
}
