package org.softuni.productshop.services;

import org.modelmapper.ModelMapper;
import org.softuni.productshop.domain.entity.Category;
import org.softuni.productshop.domain.models.services.CategoryServiceModel;
import org.softuni.productshop.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryServiceModel saveCategory(CategoryServiceModel categoryService) {
        Category category = this.modelMapper.map(categoryService, Category.class);
        try {
            category = this.categoryRepository.saveAndFlush(category);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return this.modelMapper.map(category, CategoryServiceModel.class);
    }

    @Override
    public void deleteCategory(String id) {
        try {
            this.categoryRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public CategoryServiceModel getCategoryById(String id) {
        Category category = this.categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found!"));
        return this.modelMapper.map(category, CategoryServiceModel.class);
    }

    @Override
    public CategoryServiceModel getCategoryByName(String name) {
        Category category = this.categoryRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Category not found!"));
        return this.modelMapper.map(category, CategoryServiceModel.class);
    }

    @Override
    public List<CategoryServiceModel> getAllCategories() {
        List<Category> categories = this.categoryRepository.findAllOrderByName();
        if (categories == null) {
            return new ArrayList<>();
        }
        return categories.stream()
                .map(category -> this.modelMapper.map(category, CategoryServiceModel.class))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean categoryExist(String name) {
        return getCategoryByName(name) != null;
    }
}
