package com.ngl.clone.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {


    UserDetailsService userDetailsService;
    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Autowired
    public void setUnauthorizedHandler(AuthEntryPointJwt unauthorizedHandler) {
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        //authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()    .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        ; // Disable CSRF protection - FOR TEST ONLY, NOT SURE IF REQUIRED IN ANGULAR
        http.cors();
        http
                .authorizeHttpRequests(auth -> {
                    try {
                        auth
                                //temporary
                                .requestMatchers(request ->  request.getServletPath().equals("/ws")).permitAll() // Allow unauthenticated access for POST request on /question
                                .requestMatchers(request ->  request.getServletPath().equals("/login")).permitAll() // Allow unauthenticated access for POST request on /question
                                .requestMatchers(request -> request.getMethod().equals("POST") && request.getServletPath().equals("/question")).permitAll() // Allow unauthenticated access for POST request on /question
                                .requestMatchers(request -> request.getMethod().equals("GET") && request.getServletPath().matches("/question/\\d+")).authenticated()
                                .requestMatchers(request -> request.getMethod().equals("GET") && request.getServletPath().equals("/question")).authenticated() // Require authentication for GET request on /question
                                .requestMatchers(request -> request.getMethod().equals("POST") && request.getServletPath().equals("/question/read")).authenticated() // Allow unauthenticated access for POST request on /question

                                .and()
                        .httpBasic();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }); // Use Basic Authentication
        //return http.build();
        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
