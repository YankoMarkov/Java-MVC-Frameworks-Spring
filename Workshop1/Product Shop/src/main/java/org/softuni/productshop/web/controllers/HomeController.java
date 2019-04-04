package org.softuni.productshop.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.productshop.domain.models.views.CategoriesViewModel;
import org.softuni.productshop.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController extends BaseController {

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public HomeController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    @PreAuthorize("isAnonymous()")
    public ModelAndView index() {
        return this.view("index");
    }

    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView home(ModelAndView modelAndView) {
        List<CategoriesViewModel> categoriesViewModels = this.categoryService.getAllCategories().stream()
                .map(category -> this.modelMapper.map(category, CategoriesViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("categories", categoriesViewModels);
        return this.view("home", modelAndView);
    }
}
