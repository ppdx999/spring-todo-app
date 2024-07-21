package com.example.demo.e2e;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import com.example.demo.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.config.CustomUserDetailsService;
import com.example.demo.service.TokenService;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenService tokenService;

    @MockBean
    private CustomUserDetailsService userDetailsService;

    @Mock
    private Authentication authentication;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String jwtToken;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword(passwordEncoder.encode("password"));

        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(user);
        when(authentication.getName()).thenReturn("testuser");
        when(authentication.getAuthorities()).thenReturn(Collections.emptyList());

        jwtToken = tokenService.generateToken(authentication);
    }

    @Test
    public void testAuthenticateSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/token")
                .with(httpBasic("testuser", "password")))
                .andExpect(status().isOk());
    }

    @Test
    public void testAccessProtectedResourceWithValidToken() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/auth/check")
                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    public void testAccessProtectedResourceWithInvalidToken() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/auth/check")
                .header("Authorization", "Bearer invalid_token"))
                .andExpect(status().isUnauthorized());
    }
}
