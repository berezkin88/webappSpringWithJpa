package com.javaTask.service;

import com.javaTask.model.Product;
import com.javaTask.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * @author Aleksandr Beryozkin
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductServiceTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testFindAll() {
        List<Product> outputs = null;

        outputs = productRepository.findAll();

        // from data.sql
        assertThat(outputs).isNotNull().hasSize(3);
    }

    @Test
    public void testSave() {
        Product product1 = new Product("product", 10.1, "about product");

        List<Product> outputs = null;

        productRepository.save(product1);

        outputs = productRepository.findAll();

        // from data.sql + 1
        assertThat(outputs).isNotNull().hasSize(4);
    }

    @Test
    public void testFindById() {
        Product product1 = new Product("product", 10.1, "about product");
        Product output = null;

        entityManager.persistAndFlush(product1);

        output = productRepository.findById(product1.getId()).get();

        assertThat(output).isNotNull();
        assertThat(output.getTitle()).isEqualTo(product1.getTitle());
    }

    @Test
    public void testDeleteById() {
        Product product1 = new Product("product", 10.1, "about product");

        List<Product> outputs = null;

        entityManager.persistAndFlush(product1);

        productRepository.deleteById(product1.getId());

        outputs = productRepository.findAll();

        // from data.sql
        assertThat(outputs).isNotNull().hasSize(3);
    }
}
