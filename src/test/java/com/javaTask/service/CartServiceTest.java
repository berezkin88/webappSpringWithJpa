package com.javaTask.service;

import com.javaTask.model.Cart;
import com.javaTask.model.enums.Status;
import com.javaTask.repository.CartRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.*;
/**
 * @author Aleksandr Beryozkin
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public class CartServiceTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CartRepository cartRepository;

    @Before
    public void cleanUpDB() {
        entityManager.clear();
    }

    @Test
    public void testFindAll() {
        Cart cart1 = new Cart(11l, Status.OPEN, System.currentTimeMillis());
        Cart cart2 = new Cart(22l, Status.OPEN, System.currentTimeMillis());

        entityManager.persist(cart1);
        entityManager.persist(cart2);
        entityManager.flush();

        List<Cart> results;

        results = cartRepository.findAll();

        assertThat(results).isNotNull().hasSize(2);
    }

    @Test
    public void testSave() {
        Cart cart1 = new Cart(11l, Status.OPEN, System.currentTimeMillis());

        cartRepository.save(cart1);

        List<Cart> results;

        results = cartRepository.findAll();

        assertThat(results).isNotNull().hasSize(1).contains(cart1);
    }

    @Test
    public void testFindById() {
        Cart cart1 = new Cart(11l, Status.OPEN, System.currentTimeMillis());
        Cart result = null;

        entityManager.persistAndFlush(cart1);

        result = cartRepository.findById(cart1.getId()).get();

        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(11l);
    }

    @Test
    public void testDeleteById() {
        Cart cart1 = new Cart(11l, Status.OPEN, System.currentTimeMillis());

        entityManager.persistAndFlush(cart1);

        cartRepository.deleteById(cart1.getId());

        List<Cart> results;

        results = cartRepository.findAll();

        assertThat(results).isEmpty();
    }

    @Test
    public void testGetCartsByUserIdAndOpen() {
        Cart cart1 = new Cart(11l, Status.OPEN, System.currentTimeMillis());
        Cart cart2 = new Cart(11l, Status.CLOSED, System.currentTimeMillis());

        entityManager.persist(cart1);
        entityManager.persist(cart2);
        entityManager.flush();

        Cart output = null;

        output = cartRepository.getCartsByUserIdAndOpen(11l, Status.OPEN);

        assertThat(output).isNotNull();
        assertThat(output.getUserId()).isEqualTo(cart1.getUserId());
        assertThat(output.getTime()).isEqualTo(cart1.getTime());
    }

    @Test
    public void testCloseCart() {
        Cart cart1 = new Cart(11l, Status.OPEN, System.currentTimeMillis());

        entityManager.persistAndFlush(cart1);

        Cart cart = cartRepository.findById(cart1.getId()).get();

        cart.setStatus(Status.CLOSED);

        cartRepository.save(cart);

        Cart output = cartRepository.findById(cart1.getId()).get();

        assertThat(output).isNotNull();
        assertThat(output.getUserId()).isEqualTo(cart1.getUserId());
        assertThat(output.getStatus()).isEqualTo(Status.CLOSED);
    }
}
