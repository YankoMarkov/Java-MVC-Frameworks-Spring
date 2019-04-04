package org.softuni.productshop.domain.models.views;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class ProductEditViewModel {

    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private Set<String> categories;

    public ProductEditViewModel() {
        this.categories = new HashSet<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<String> getCategories() {
        return categories;
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }
}
