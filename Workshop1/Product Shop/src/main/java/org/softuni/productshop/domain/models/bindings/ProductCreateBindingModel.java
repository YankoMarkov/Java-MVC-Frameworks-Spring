package org.softuni.productshop.domain.models.bindings;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class ProductCreateBindingModel {

    private String name;
    private String description;
    private BigDecimal price;
    private MultipartFile imageUrl;
    private Set<String> categories;

    public ProductCreateBindingModel() {
        this.categories = new HashSet<>();
    }

    @NotNull(message = "Name cannot be null.")
    @Size(min = 3, max = 30, message = "Name must be in range [3 - 30] symbols.")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "Description cannot be null.")
    @NotBlank(message = "Description cannot be empty.")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message = "Price cannot be null.")
    @DecimalMin(value = "0.001", message = "Price must be greater than 0")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @NotNull(message = "Image cannot be null.")
    public MultipartFile getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(MultipartFile imageUrl) {
        this.imageUrl = imageUrl;
    }

    @NotNull(message = "Category cannot be null.")
    @NotEmpty(message = "Category cannot be empty.")
    public Set<String> getCategories() {
        return categories;
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }
}
