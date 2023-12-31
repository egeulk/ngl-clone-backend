package com.ngl.clone.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceBean extends InMemoryUserDetailsManager  {
    public UserDetailsServiceBean() {
        super(createUser());
    }

    private static UserDetails createUser() {
        return User.builder()
                .username("user")
                .password("{bcrypt}$2a$10$/0nbFVtCCHUcFdezlFC42uuJaYzfhUHZZ37PUmDw.KprAloOw00Ni")
                .roles("USER")
                .build();
    }
}
