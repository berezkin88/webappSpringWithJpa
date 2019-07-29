package com.javaTask.controller;

import com.javaTask.model.Order;
import com.javaTask.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.logging.Logger;

/**
 * @author Aleksandr Beryozkin
 */

@Controller
@RequestMapping(path = "/api/order")
public class OrderController {

    private static final Logger LOG = Logger.getLogger(OrderController.class.getName());

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<String> createOrder(@RequestParam("cartid") Long cartId, @RequestParam("productid") Long productId, @RequestParam("quantity") int quantity) {
        LOG.info("Cart id is " + cartId);

        Order order = new Order();

        order.setCartId(cartId);
        order.setProductId(productId);
        order.setQuantity(quantity);

        LOG.info("creating new order...");

        return new ResponseEntity<>("Order created", HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<String> deleteOrder(@RequestParam(value = "orderid", required = false) Long orderId) {

        if (orderId == null)
            return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);

        orderService.deleteById(orderId);

        return new ResponseEntity<>("Order deleted", HttpStatus.OK);
    }
}
