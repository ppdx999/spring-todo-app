package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByUsername() {
        User user = new User();
        user.setUsername("testuser");

        when(userRepository.findByUsername("testuser")).thenReturn(user);

        User foundUser = userService.findByUsername("testuser");
        assertEquals("testuser", foundUser.getUsername());
    }

    @Test
    void testFindById() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.findById(1L);
        assertTrue(foundUser.isPresent());
        assertEquals("testuser", foundUser.get().getUsername());
    }

    @Test
    void testSaveUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");

        when(userRepository.save(user)).thenReturn(user);

        userService.save(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdateUser() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setUsername("existinguser");
        existingUser.setPassword("password");

        User newUserData = new User();
        newUserData.setUsername("updateduser");
        newUserData.setPassword("newpassword");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        Optional<User> updatedUser = userService.update(1L, newUserData);
        assertTrue(updatedUser.isPresent());
        assertEquals("updateduser", updatedUser.get().getUsername());
        assertEquals("newpassword", updatedUser.get().getPassword());
    }

    @Test
    void testUpdateUserNotFound() {
        User newUserData = new User();
        newUserData.setUsername("updateduser");
        newUserData.setPassword("newpassword");

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<User> updatedUser = userService.update(1L, newUserData);
        assertTrue(updatedUser.isEmpty());
    }

    @Test
    void testDeleteUser() {
        User user = new User();
        user.setId(1L);

        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        boolean isDeleted = userService.delete(1L);
        assertTrue(isDeleted);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUserNotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);

        boolean isDeleted = userService.delete(1L);
        assertFalse(isDeleted);
        verify(userRepository, never()).deleteById(1L);
    }
}
