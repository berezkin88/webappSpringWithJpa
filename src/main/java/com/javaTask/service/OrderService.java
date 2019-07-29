package com.javaTask.service;

import com.javaTask.model.Order;
import com.javaTask.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Aleksandr Beryozkin
 */

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).get();
    }

    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    public List<Order> getOrdersByCartId(Long id) {
        return orderRepository.getOrdersByCartId(id);
    }
}