package com.github.microwww.security.serve.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RootController {

    @PostMapping("/login/account")
    public void login(String account, String password) {
    }

}
