package com.javaTask.controller;

import com.javaTask.model.Product;
import com.javaTask.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Aleksandr Beryozkin
 */

@RestController
@RequestMapping(path = "/api")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "/products")
    public ResponseEntity<List<Product>> loadProducts() {
        List<Product> store = productService.findAll();

        return new ResponseEntity<>(store, HttpStatus.FOUND);
    }
}
