package com.cursos.api.springsecuritycourse.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfig {

    @Autowired
    private AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrfConfig->csrfConfig.disable())
                .sessionManagement(sessionManager->sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .authorizeHttpRequests(httpRequest->{
                    httpRequest.requestMatchers(HttpMethod.POST, "/login").permitAll();
                    httpRequest.requestMatchers(HttpMethod.GET, "/login").permitAll();
                    httpRequest.requestMatchers(HttpMethod.POST, "/usuarios/registro").permitAll();

                    httpRequest.requestMatchers("/productos/**").hasRole("admin");
                    httpRequest.requestMatchers("/categorias/**").hasRole("admin");
                    httpRequest.requestMatchers(HttpMethod.GET, "/productos/**").hasRole("assistant");
                    httpRequest.requestMatchers(HttpMethod.PUT, "/productos/{id}").hasRole("assistant");
                    httpRequest.requestMatchers(HttpMethod.GET, "/categorias/page/{page}").hasRole("assistant");
                    httpRequest.requestMatchers(HttpMethod.GET, "/categorias/{id}").hasRole("assistant");
                    httpRequest.requestMatchers(HttpMethod.PUT, "/categorias/{id}").hasRole("assistant");
                }).build();

    }


}
