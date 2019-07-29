package com.javaTask.repository;

import com.javaTask.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Aleksandr Beryozkin
 */

public interface ProductRepository extends JpaRepository<Product, Long> {
}
