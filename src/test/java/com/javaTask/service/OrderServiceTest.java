package com.javaTask.service;

import com.javaTask.model.Order;
import com.javaTask.repository.OrderRepository;
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
public class OrderServiceTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testFindAll() {
        Order order1 = new Order(111l, 1, 11l);
        Order order2 = new Order(222l, 2, 22l);
        List<Order> outputs = null;

        entityManager.persist(order1);
        entityManager.persist(order2);
        entityManager.flush();

        outputs = orderRepository.findAll();

        assertThat(outputs).isNotNull();
        assertThat(outputs).hasSize(2);
    }

    @Test
    public void testSave() {
        Order order1 = new Order(111l, 1, 11l);
        List<Order> outputs = null;

        orderRepository.save(order1);

        outputs = orderRepository.findAll();

        assertThat(outputs).isNotNull();
        assertThat(outputs.get(0).getProductId()).isEqualTo(order1.getProductId());
        assertThat(outputs.get(0).getQuantity()).isEqualTo(order1.getQuantity());
    }

    @Test
    public void testFindById() {
        Order order1 = new Order(111l, 1, 11l);
        Order output = null;

        entityManager.persistAndFlush(order1);

        output = orderRepository.findById(order1.getId()).get();

        assertThat(output).isNotNull();
        assertThat(output.getCartId()).isEqualTo(order1.getCartId());
    }

    @Test
    public void testDeleteById() {
        Order order1 = new Order(111l, 1, 11l);
        List<Order> outputs = null;

        entityManager.persistAndFlush(order1);

        orderRepository.deleteById(order1.getId());

        outputs = orderRepository.findAll();

        assertThat(outputs).isEmpty();
    }

    @Test
    public void testGetOrdersByCartId() {
        Order order1 = new Order(111l, 1, 11l);
        Order order2 = new Order(222l, 2, 22l);
        List<Order> outputs = null;

        entityManager.persist(order1);
        entityManager.persist(order2);
        entityManager.flush();

        outputs = orderRepository.getOrdersByCartId(order2.getCartId());

        assertThat(outputs).isNotNull();
        assertThat(outputs).hasSize(1);
        assertThat(outputs.get(0).getCartId()).isEqualTo(order2.getCartId());
    }
}
