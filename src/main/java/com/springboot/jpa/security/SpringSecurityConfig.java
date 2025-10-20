package com.springboot.jpa.security;

import com.springboot.jpa.security.filter.JwtAuthenticationFilter;
import com.springboot.jpa.security.filter.JwtValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class SpringSecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "content-type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws  Exception {
        return  http.authorizeHttpRequests((authz)-> authz
                        // Usuarios: solo el admin tiene acceso
                        .requestMatchers("/api/users/**").hasRole("ADMIN")

                        // Productos, tipos y localizaciones: solo lectura para el usuario
                        .requestMatchers(HttpMethod.GET, "/api/products/**", "/api/types/**", "/api/localizations/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/products/**", "/api/types/**", "/api/localizations/**").hasRole("ADMIN")

                        // Inventario: el usuario puede crear, pero no modificar ni eliminar
                        .requestMatchers(HttpMethod.GET, "/api/inventory/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/inventory/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/inventory/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/inventory/**").hasRole("ADMIN")

                        // Categorías: lo mismo que el inventario
                        .requestMatchers(HttpMethod.GET, "/api/categories/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/categories/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/categories/**").hasRole("ADMIN")

                        // Clientes: Los usuarios solo pueden ver la lista
                        .requestMatchers(HttpMethod.GET, "/api/clients/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/clients/**").hasRole("ADMIN")

                        // Ordenes: el usuario puede ver y crear ordenes pero solo el admin puede modificar y eliminar
                        .requestMatchers(HttpMethod.GET, "/api/orders/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/orders/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/orders/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/orders/**").hasRole("ADMIN")

                        .anyRequest().authenticated())
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtValidationFilter(authenticationManager()))
                .csrf(config -> config.disable())
                .sessionManagement(management ->
                        management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilterRegistration() {
        FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<>(
                new CorsFilter(corsConfigurationSource()));
        corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return corsBean;
    }
}
