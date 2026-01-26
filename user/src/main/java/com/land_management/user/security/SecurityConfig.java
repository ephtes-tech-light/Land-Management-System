package com.land_management.user.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{
        httpSecurity.csrf(csrf->csrf.disable())
                .authorizeHttpRequests(
                        auth-> auth.
                                requestMatchers(HttpMethod.POST,"/api/user/register").permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/user/getbyid/**").permitAll()
                                .anyRequest().authenticated()
                ).oauth2ResourceServer(
                        oauth->oauth.jwt(jwt->{}));
        return httpSecurity.build();
    }
}
