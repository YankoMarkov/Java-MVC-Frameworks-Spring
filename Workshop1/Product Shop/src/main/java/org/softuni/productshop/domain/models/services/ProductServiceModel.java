package org.softuni.productshop.domain.models.services;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class ProductServiceModel extends BaseServiceModel {

    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private Set<CategoryServiceModel> categories;

    public ProductServiceModel() {
        this.categories = new HashSet<>();
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Set<CategoryServiceModel> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryServiceModel> categories) {
        this.categories = categories;
    }
}
