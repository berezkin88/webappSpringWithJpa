package com.javaTask.controller;

import com.javaTask.model.Cart;
import com.javaTask.model.User;
import com.javaTask.model.enums.Status;
import com.javaTask.service.CartService;
import com.javaTask.service.UserService;
import org.springframework.http.HttpHeaders;
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
public class LoginController {

    private static final Logger LOG = Logger.getLogger(LoginController.class.getName());
    private final UserService userService;
    private final CartService cartService;

    private static User user = null;
    private static Cart cart = null;

    public LoginController(UserService userService, CartService cartService) {
        this.userService = userService;
        this.cartService = cartService;
    }

    @PostMapping(path="/login")
    public ResponseEntity<Map<String, Long>> loginAndCreateUserAndCart(@RequestParam("username") String username, @RequestParam("password") String password) {
        Map<String, Long> cartUser = new HashMap<>();

        createUserAndCart(username, password);

        cartUser.put("userId", user.getId());
        cartUser.put("cartId", cart.getId());

        return new ResponseEntity<>(cartUser, HttpStatus.CREATED);
    }

    @GetMapping(path="/login")
    public Object loginAndCreateUserAndCart(@RequestParam(value = "userid", required = false) Long userId) {
        Map<String, Long> cartUser = new HashMap<>();

//        Redirect to homepage if user id is NULL
        if (userId == null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/index.html");
            return new ResponseEntity<String>(headers,HttpStatus.FOUND);
        }

        cart = createNewCart(userId);

        cartUser.put("userId", user.getId());
        cartUser.put("cartId", cart.getId());

        return new ResponseEntity<Map<String, Long>>(cartUser, HttpStatus.CREATED);
    }

    private Cart createNewCart(Long userId)  {
        Cart newCart = new Cart();

        LOG.info("initializing cart...");
        newCart.setUserId(user.getId());
        newCart.setStatus(Status.OPEN);
        newCart.setTime(System.currentTimeMillis());

        cartService.save(newCart);

        LOG.info("new cart created" + newCart.toString());

        return newCart;
    }

    private void createUserAndCart(String username, String password) {
        LOG.info("Creating user... ");

        if (user == null)
            user = new User();

        user.setUsername(username);
        user.setPassword(password);

        userService.save(user);
        LOG.info(user.toString());

        if (cart == null)
            cart = new Cart();

        LOG.info("initializing cart...");
        cart.setUserId(user.getId());
        cart.setStatus(Status.OPEN);
        cart.setTime(System.currentTimeMillis());

        cartService.save(cart);
        LOG.info(cart.toString());
    }
}
