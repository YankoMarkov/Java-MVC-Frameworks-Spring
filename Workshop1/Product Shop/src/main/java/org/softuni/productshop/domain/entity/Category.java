package org.softuni.productshop.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "categories")
public class Category extends BaseEntity {

    private String name;

    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
