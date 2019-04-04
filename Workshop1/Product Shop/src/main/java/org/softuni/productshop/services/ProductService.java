package org.softuni.productshop.services;

import org.softuni.productshop.domain.models.services.ProductServiceModel;

import java.util.List;

public interface ProductService {

    ProductServiceModel saveProduct(ProductServiceModel productService);

    List<ProductServiceModel> getAllProducts();

    ProductServiceModel getProductByName(String name);

    ProductServiceModel getProductById(String id);

    void deleteProduct(String id);
}
