package org.softuni.productshop.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.productshop.domain.models.bindings.ProductCreateBindingModel;
import org.softuni.productshop.domain.models.bindings.ProductEditBindingModel;
import org.softuni.productshop.domain.models.services.CategoryServiceModel;
import org.softuni.productshop.domain.models.services.ProductServiceModel;
import org.softuni.productshop.domain.models.views.ProductAllViewModel;
import org.softuni.productshop.domain.models.views.ProductDetailsViewModel;
import org.softuni.productshop.domain.models.views.ProductEditViewModel;
import org.softuni.productshop.services.CategoryService;
import org.softuni.productshop.services.CloudinaryService;
import org.softuni.productshop.services.ProductService;
import org.softuni.productshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController extends BaseController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final CloudinaryService cloudinaryService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService, CloudinaryService cloudinaryService, UserService userService, ModelMapper modelMapper) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.cloudinaryService = cloudinaryService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    public ModelAndView add(@ModelAttribute("productCreate") ProductCreateBindingModel productCreate) {
        return this.view("add-product");
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    public ModelAndView addConfirm(@Valid @ModelAttribute("productCreate") ProductCreateBindingModel productCreate,
                                   BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            return view("add-product");
        }
        ProductServiceModel productServiceModel = this.modelMapper.map(productCreate, ProductServiceModel.class);
        productServiceModel.setCategories(
                this.categoryService.getAllCategories().stream()
                        .filter(category -> productCreate.getCategories().contains(category.getId()))
                        .collect(Collectors.toSet())
        );
        productServiceModel.setImageUrl(
                this.cloudinaryService.uploadImage(productCreate.getImageUrl()));
        this.productService.saveProduct(productServiceModel);
        return this.redirect("/products/all");
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    public ModelAndView all(ModelAndView modelAndView) {
        List<ProductAllViewModel> productAllViewModels = this.productService.getAllProducts().stream()
                .map(product -> this.modelMapper.map(product, ProductAllViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("products", productAllViewModels);
        return this.view("all-products", modelAndView);
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView details(@PathVariable String id, ModelAndView modelAndView) {
        ProductServiceModel productServiceModel = this.productService.getProductById(id);
        ProductDetailsViewModel productDetailsViewModel = this.modelMapper.map(productServiceModel, ProductDetailsViewModel.class);
        modelAndView.addObject("product", productDetailsViewModel);
        return this.view("details", modelAndView);
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    public ModelAndView edit(@PathVariable String id, ModelAndView modelAndView) {
        ProductServiceModel productServiceModel = this.productService.getProductById(id);
        ProductEditViewModel productEditViewModel = this.modelMapper.map(productServiceModel, ProductEditViewModel.class);
        productEditViewModel.setCategories(
                productServiceModel.getCategories().stream()
                        .map(CategoryServiceModel::getName)
                        .collect(Collectors.toSet())
        );
        modelAndView.addObject("product", productEditViewModel);
        return this.view("edit-product", modelAndView);
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    public ModelAndView editConfirm(@PathVariable String id,
                                    @Valid @ModelAttribute("productEdit") ProductEditBindingModel productEdit,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return this.view("edit-product");
        }
        ProductServiceModel productServiceModel = productService.getProductById(id);
        this.modelMapper.map(productEdit, productServiceModel);
        this.productService.saveProduct(productServiceModel);
        return this.redirect("/products/details/" + id);
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    public ModelAndView delete(@PathVariable String id, ModelAndView modelAndView) {
        ProductServiceModel productServiceModel = this.productService.getProductById(id);
        ProductEditViewModel productEditViewModel = this.modelMapper.map(productServiceModel, ProductEditViewModel.class);
        productEditViewModel.setCategories(
                productServiceModel.getCategories().stream()
                        .map(CategoryServiceModel::getName)
                        .collect(Collectors.toSet())
        );
        modelAndView.addObject("product", productEditViewModel);
        return this.view("delete-product", modelAndView);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    public ModelAndView deleteConfirm(@PathVariable String id) {
        this.productService.deleteProduct(id);
        return this.redirect("/products/all");
    }
}
