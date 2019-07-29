package com.javaTask.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Aleksandr Beryozkin
 */

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index.html";
    }
}
