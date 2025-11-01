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

    public static final String API_INVENTORY = "/api/inventory/**";
    public static final String API_CATEGORIES = "/api/categories/**";
    public static final String API_CLIENTS = "/api/clients/**";
    public static final String API_ORDERS = "/api/orders/**";
    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";
    public static final String API_PRODUCTS = "/api/products/**";
    public static final String API_TYPES = "/api/types/**";
    public static final String API_LOCALIZATIONS = "/api/localizations/**";
    public static final String API_USERS = "/api/users/**";
    public static final String SWAGGER_UI = "/swagger-ui/**";
    public static final String V_3_API_DOCS = "/v3/api-docs/**";


    private final AuthenticationConfiguration authenticationConfiguration;

    public SpringSecurityConfig(@Autowired AuthenticationConfiguration authenticationConfiguration) {
        this.authenticationConfiguration = authenticationConfiguration;
    }

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
        return  http.authorizeHttpRequests(authz-> authz
                        .requestMatchers(SWAGGER_UI).permitAll()
                        .requestMatchers(V_3_API_DOCS).permitAll()

                        // Usuarios: solo el admin tiene acceso
                        .requestMatchers(API_USERS).hasRole(ADMIN)

                        // Productos, tipos y localizaciones: solo lectura para el usuario
                        .requestMatchers(HttpMethod.GET, API_PRODUCTS, API_TYPES, API_LOCALIZATIONS).hasAnyRole(USER, ADMIN)
                        .requestMatchers(API_PRODUCTS, API_TYPES, API_LOCALIZATIONS).hasRole(ADMIN)

                        // Inventario: el usuario puede crear, pero no modificar ni eliminar
                        .requestMatchers(HttpMethod.GET, API_INVENTORY).hasAnyRole(USER, ADMIN)
                        .requestMatchers(HttpMethod.POST, API_INVENTORY).hasAnyRole(USER, ADMIN)
                        .requestMatchers(HttpMethod.PUT, API_INVENTORY).hasRole(ADMIN)
                        .requestMatchers(HttpMethod.DELETE, API_INVENTORY).hasRole(ADMIN)

                        // Categorías: lo mismo que el inventario
                        .requestMatchers(HttpMethod.GET, API_CATEGORIES).hasAnyRole(USER, ADMIN)
                        .requestMatchers(HttpMethod.POST, API_CATEGORIES).hasAnyRole(USER, ADMIN)
                        .requestMatchers(HttpMethod.PUT, API_CATEGORIES).hasRole(ADMIN)
                        .requestMatchers(HttpMethod.DELETE, API_CATEGORIES).hasRole(ADMIN)

                        // Clientes: Los usuarios solo pueden ver la lista
                        .requestMatchers(HttpMethod.GET, API_CLIENTS).hasAnyRole(USER, ADMIN)
                        .requestMatchers(API_CLIENTS).hasRole(ADMIN)

                        // Ordenes: el usuario puede ver y crear ordenes pero solo el admin puede modificar y eliminar
                        .requestMatchers(HttpMethod.GET, API_ORDERS).hasAnyRole(USER, ADMIN)
                        .requestMatchers(HttpMethod.POST, API_ORDERS).hasAnyRole(USER, ADMIN)
                        .requestMatchers(HttpMethod.PUT, API_ORDERS).hasRole(ADMIN)
                        .requestMatchers(HttpMethod.DELETE, API_ORDERS).hasRole(ADMIN)

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
