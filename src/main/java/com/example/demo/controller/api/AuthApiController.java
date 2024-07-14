package com.example.demo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.TokenService;

@RestController
@RequestMapping("/api/auth")
public class AuthApiController {
    @Autowired
    private TokenService tokenService;

    @PostMapping("/token")
    public String generateToken(Authentication authentication) {
        return tokenService.generateToken(authentication);
    }
}
