package com.javaTask.service;

import com.javaTask.model.Cart;
import com.javaTask.model.enums.Status;
import com.javaTask.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author Aleksandr Beryozkin
 */

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart findById(Long id) {
        return cartRepository.getOne(id);
    }

    public void deleteById(Long id) {
        cartRepository.deleteById(id);
    }

    public Cart findCartsByUserIdAndOpen(Long id) {
        return cartRepository.getCartsByUserIdAndOpen(id, Status.OPEN);
    }

    public void closeCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId).get();

        cart.setStatus(Status.CLOSED);

        save(cart);
    }
}
