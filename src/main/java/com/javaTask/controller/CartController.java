package com.javaTask.controller;

import com.javaTask.service.CartService;
import com.javaTask.service.ProductService;
import com.javaTask.service.ProductTOService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Aleksandr Beryozkin
 */

@RestController
@RequestMapping(path = "/api")
public class CartController {

    private static final Logger LOG = Logger.getLogger(CartController.class.getName());

    private final ProductTOService productTOService;
    private final CartService cartService;

    public CartController(ProductTOService productTOService, CartService cartService) {
        this.productTOService = productTOService;
        this.cartService = cartService;
    }

    @GetMapping(path = "/cart")
    public ResponseEntity<Map<String, Object>> loadCartInfo(@RequestParam("userid") Long userId, @RequestParam("cartid") Long cartId) {
        Map<String, Object> results = new HashMap<>();

        results.put("userId", userId);
        results.put("cartId", cartId);
        results.put("results", productTOService.getAllProductsByCartId(cartId));

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PutMapping(path = "/checkout")
    public ResponseEntity<Map<String, Long>> closeCart(@RequestParam("userid") Long userId, @RequestParam("cartid") Long cartId) {
        Map<String, Long> userAndCart = new HashMap<>();

        cartService.closeCart(cartId);

        userAndCart.put("userId", userId);
        userAndCart.put("cartId", cartId);

        return new ResponseEntity<>(userAndCart, HttpStatus.OK);
    }
}
