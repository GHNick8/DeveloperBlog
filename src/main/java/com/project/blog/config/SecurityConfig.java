package com.project.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
                // Allow access to the H2 console without authentication
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/posts/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/posts/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/posts/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/posts/**").permitAll()
                .anyRequest().authenticated()
            )
            // Disable frame options for H2 console
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        return new InMemoryUserDetailsManager(
            User.builder()
                .passwordEncoder(passwordEncoder::encode)
                .username("admin")
                .password("adminpassword")
                .roles("ADMIN")
                .build(),
            User.builder()
                .passwordEncoder(passwordEncoder::encode)
                .username("reader")
                .password("readerpassword")
                .roles("READER")
                .build()
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
