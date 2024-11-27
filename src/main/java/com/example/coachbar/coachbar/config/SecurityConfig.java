package com.example.coachbar.coachbar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Use the lambda-based configuration
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/users/create", "/api/users/update").hasRole("ADMIN")
                                .requestMatchers("/api/users/{id}").hasRole("USER")
                                .anyRequest().authenticated()
                )
                .httpBasic(httpBasic -> httpBasic.realmName("MyApp")); // Use the new customizer function for Basic authentication

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCrypt for password encoding
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.withUsername("admin")
                        .password(passwordEncoder().encode("adminpass"))
                        .roles("ADMIN")
                        .build(),
                User.withUsername("user")
                        .password(passwordEncoder().encode("userpass"))
                        .roles("USER")
                        .build()
        );
    }
}
