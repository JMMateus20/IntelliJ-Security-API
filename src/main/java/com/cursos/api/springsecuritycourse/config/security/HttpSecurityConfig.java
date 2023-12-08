package com.cursos.api.springsecuritycourse.config.security;

import com.cursos.api.springsecuritycourse.config.security.filter.JwtAuthenticationFilter;
import com.cursos.api.springsecuritycourse.config.security.handler.CustomAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled = true)
public class HttpSecurityConfig {

    @Autowired
    private AuthenticationProvider authProvider;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrfConfig->csrfConfig.disable())
                .sessionManagement(sessionManager->sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                .authorizeHttpRequests(httpRequest->{
                    httpRequest.requestMatchers(HttpMethod.POST, "/login").permitAll();
                    httpRequest.requestMatchers(HttpMethod.GET, "/login").permitAll();
                    httpRequest.requestMatchers(HttpMethod.POST, "/usuarios/registro").permitAll();

                    httpRequest.requestMatchers(HttpMethod.POST,"/productos").hasRole("admin");
                    httpRequest.requestMatchers(HttpMethod.POST,"/categorias").hasRole("admin");
                    httpRequest.requestMatchers(HttpMethod.PUT, "/categorias/status/{id}").hasRole("admin");
                    httpRequest.requestMatchers(HttpMethod.PUT, "/productos/status/{id}").hasRole("admin");
                    httpRequest.requestMatchers(HttpMethod.GET, "/productos/**").hasAnyRole("assistant", "admin");
                    httpRequest.requestMatchers(HttpMethod.PUT, "/productos/{id}").hasAnyRole("assistant", "admin");
                    //expresion regular: httpRequest.requestMatchers(RegexRequestMatcher.regexMatcher(HttpMethod.GET, "/productos/[0-9]*")).hasAnyRole("assistant", "admin");
                    httpRequest.requestMatchers(HttpMethod.GET, "/categorias/**").hasAnyRole("assistant", "admin");
                    httpRequest.requestMatchers(HttpMethod.PUT, "/categorias/{id}").hasAnyRole("assistant", "admin");

                    httpRequest.anyRequest().authenticated();



                    //CONTRASEÑA USUSARIOS: JMMATEUS20=12345, Carlos123=13579, alejam15=6789
                })
                .exceptionHandling(exceptionConfig->{
                    exceptionConfig.authenticationEntryPoint(authenticationEntryPoint);
                    exceptionConfig.accessDeniedHandler(accessDeniedHandler);
                })
                .build();


    }


}
