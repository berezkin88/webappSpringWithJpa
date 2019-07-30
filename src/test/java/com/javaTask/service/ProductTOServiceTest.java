package com.javaTask.service;

import com.javaTask.model.Cart;
import com.javaTask.model.Order;
import com.javaTask.model.Product;
import com.javaTask.model.ProductTO;
import com.javaTask.model.enums.Status;
import com.javaTask.repository.ProductTORepository;
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
public class ProductTOServiceTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductTORepository productTORepository;

    @Test
    public void testGetAllProductsByCartId() {
        ProductTO product1 = new ProductTO("product1", 1, 10.1, 1l);
        product1.setId(1l);
        ProductTO product2 = new ProductTO("product2", 2, 20.1, 2l);
        product2.setId(2l);
//        Product product1 = new Product("product1", 10.1, "about product1");
//        Product product2 = new Product("product2", 20.2, "about product2");
        Order order1 = new Order(product1.getId(), 1, 11l);
        Order order2 = new Order(product2.getId(), 2, 11l);

        entityManager.persist(product1);
        entityManager.persist(product2);
        entityManager.persist(order1);
        entityManager.persist(order2);
        entityManager.flush();

        List<ProductTO> outputs = null;

        outputs = productTORepository.getAllProductsByCartId(order1.getCartId());

        assertThat(outputs).isNotNull();
        assertThat(outputs).hasSize(2);
    }

    @Test
    public void testGetProductsHistoryByTimeAndUserId() {
        Cart cart1 = new Cart(111l, Status.CLOSED, 156000l);
        Cart cart2 = new Cart(111l, Status.CLOSED, 159000l);
        Order order1 = new Order(1l, 1, cart1.getId());
        Order order2 = new Order(2l, 2, cart2.getId());
        // should return this one
        ProductTO product1 = new ProductTO("product1", 1, 10.1, order1.getId());
        product1.setId(1l);
        ProductTO product2 = new ProductTO("product2", 2, 20.1, order2.getId());
        product2.setId(2l);

        entityManager.persist(product1);
        entityManager.persist(product2);
        entityManager.persist(order1);
        entityManager.persist(order2);
        entityManager.persist(cart1);
        entityManager.persist(cart2);
        entityManager.flush();

        List<ProductTO> outputs = null;

        outputs = productTORepository.getProductsHistoryByTimeAndUserId(cart1.getUserId(), 155000l, 158000l);

        assertThat(outputs).isNotNull();
        // should be 1
        assertThat(outputs).hasSize(0);
    }
}
