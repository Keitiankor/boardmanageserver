package com.keitian.boardmanageserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.keitian.boardmanageserver.global.jwt.JwtAccessDeniedHandler;
import com.keitian.boardmanageserver.global.jwt.JwtSecurityConfig;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable())
                .exceptionHandling(
                        (exceptionHandling) -> exceptionHandling
                                .accessDeniedHandler(jwtAccessDeniedHandler))
                .authorizeHttpRequests(
                        (authorizeHttpRequests) -> authorizeHttpRequests.requestMatchers("/auth"))
                .apply(new JwtSecurityConfig(/* tokenProvider */));
        return http.build();
    }

}
