package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.TokenService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
     @Autowired
    private TokenService tokenService;

    @PostMapping("/token")
    public ResponseEntity<TokenResponse> generateToken(Authentication authentication) {
        var token = tokenService.generateToken(authentication);
        return ResponseEntity.ok(new TokenResponse(token));
    }

    @GetMapping("/check")
    public void checkToken() {}


    private static class TokenResponse {
        private String token;

        public TokenResponse(String token) {
            this.token = token;
        }

        
        @SuppressWarnings("unused")
        public String getToken() {
            return token;
        }

        @SuppressWarnings("unused")
        public void setToken(String token) {
            this.token = token;
        }
    }
}
