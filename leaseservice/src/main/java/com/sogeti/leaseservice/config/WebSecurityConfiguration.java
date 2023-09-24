package com.sogeti.leaseservice.config;

import com.sogeti.leaseservice.filters.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * WebSecurityConfiguration class.
 * This class is used to configure web security.
 * @Author:revathi
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfiguration {

    /**
     * The Jwt request filter.
     */
    @Autowired
    private JwtRequestFilter requestFilter;

    /**
     * This method is used to create securityFilterChain bean
     * @param http
     * @return SecurityFilterChain
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/authenticate", "/lease/**", "/swagger-ui/**",  "/swagger-resources/*","/lease-api-docs/**", "/v3/api-docs/**").permitAll()
                .and()
//                .authorizeHttpRequests().requestMatchers("/lease/**")
//                .authenticated().and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
//        http.authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest()
//                .permitAll())
//            .csrf(AbstractHttpConfigurer::disable);
//        return http.build();
    }

    /**
     * This method is used to create passwordEncoder bean
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * This method is used to create authenticationManager bean
     * @param config
     * @return AuthenticationManager
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}