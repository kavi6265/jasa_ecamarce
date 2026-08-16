package com.example.demo;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .cors(cors -> {})
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/users/register", "/api/users/login","/api/categories/**", "/api/products/**", "/api/cart/**"
                    		,"/api/orders/**", "/api/address/**","/api/payments/**","/api/wishlist/**",  "/swagger-ui/**",
                    	    "/swagger-ui.html",
                    	    "/v3/api-docs/**",
                            "/api/reviews/**","/uploads/**","/api/banners/**","/api/users/**" ).permitAll()
                    .anyRequest().authenticated()
            )
            .httpBasic(httpBasic -> httpBasic.disable());

        return http.build();
    }
}