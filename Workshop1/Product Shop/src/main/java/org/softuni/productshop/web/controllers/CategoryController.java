package org.softuni.productshop.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.productshop.domain.models.bindings.CategoryCreateBindingModel;
import org.softuni.productshop.domain.models.services.CategoryServiceModel;
import org.softuni.productshop.domain.models.views.CategoriesViewModel;
import org.softuni.productshop.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/categories")
public class CategoryController extends BaseController {

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    public ModelAndView add(@ModelAttribute("categoryAdd") CategoryCreateBindingModel categoryCreate) {
        return this.view("add-category");
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    public ModelAndView addConfirm(@Valid @ModelAttribute("categoryAdd") CategoryCreateBindingModel categoryCreate,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return this.view("add-category");
        }
        CategoryServiceModel categoryServiceModel = this.modelMapper.map(categoryCreate, CategoryServiceModel.class);

        this.categoryService.saveCategory(categoryServiceModel);
        return this.redirect("/home");
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    public ModelAndView all(ModelAndView modelAndView) {
        List<CategoriesViewModel> categoriesViewModels = this.categoryService.getAllCategories().stream()
                .map(category -> this.modelMapper.map(category, CategoriesViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("categories", categoriesViewModels);
        return this.view("all-categories", modelAndView);
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    public ModelAndView edit(@PathVariable String id, ModelAndView modelAndView) {
        CategoriesViewModel categoriesViewModel = this.modelMapper
                .map(this.categoryService.getCategoryById(id), CategoriesViewModel.class);
        modelAndView.addObject("category", categoriesViewModel);
        return this.view("edit-category", modelAndView);
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    public ModelAndView editConfirm(@Valid @ModelAttribute("categoryCreate") CategoryCreateBindingModel categoryCreate,
                                    @PathVariable String id, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return this.view("edit-category");
        }
        CategoryServiceModel categoryServiceModel = this.categoryService.getCategoryById(id);
        categoryServiceModel.setName(categoryCreate.getName());
        this.categoryService.saveCategory(categoryServiceModel);
        return this.redirect("/categories/all");
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    public ModelAndView delete(@PathVariable String id, ModelAndView modelAndView) {
        CategoriesViewModel categoriesViewModel = this.modelMapper
                .map(this.categoryService.getCategoryById(id), CategoriesViewModel.class);
        modelAndView.addObject("category", categoriesViewModel);
        return this.view("delete-category", modelAndView);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    public ModelAndView deleteConfirm(@PathVariable String id) {
        this.categoryService.deleteCategory(id);
        return this.redirect("/categories/all");
    }

    @GetMapping("/fetch")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    @ResponseBody
    public List<CategoriesViewModel> fetch() {
        return this.categoryService.getAllCategories().stream()
                .map(category -> this.modelMapper.map(category, CategoriesViewModel.class))
                .collect(Collectors.toList());
    }
}