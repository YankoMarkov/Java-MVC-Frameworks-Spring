package org.softuni.productshop.services;

import org.softuni.productshop.domain.models.services.CategoryServiceModel;

import java.util.List;

public interface CategoryService {

    CategoryServiceModel saveCategory(CategoryServiceModel categoryService);

    void deleteCategory(String id);

    CategoryServiceModel getCategoryById(String id);

    CategoryServiceModel getCategoryByName(String name);

    List<CategoryServiceModel> getAllCategories();

    boolean categoryExist(String name);
}
