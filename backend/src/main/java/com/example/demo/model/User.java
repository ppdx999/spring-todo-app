package com.example.demo.model;

import jakarta.persistence.*;

import java.util.Collections;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @OneToMany(mappedBy = "owner")
    private Set<Todo> todos;

    @Override
    public Set<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptySet();
    }
}
