package com.mcmanuel.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

public class SecurityConfig {

    public HttpSecurity configuration(HttpSecurity http){
        return http
                .authorizeHttpRequests(
                        request -> request.requestMatchers()
                )
                .cors()
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf()
                .addFilterBefore()
                .build();

    }
}
