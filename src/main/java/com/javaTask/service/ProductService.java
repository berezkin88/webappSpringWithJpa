package com.javaTask.service;

import com.javaTask.model.Product;
import com.javaTask.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Aleksandr Beryozkin
 */

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id).get();
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
