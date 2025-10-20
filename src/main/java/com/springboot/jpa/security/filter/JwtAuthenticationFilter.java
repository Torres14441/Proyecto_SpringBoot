package com.springboot.jpa.security.filter;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.jpa.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import java.io.IOException;
import java.util.*;

import static com.springboot.jpa.security.TokenJwtConfig.*;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{
        User user = null;
        String username = null;
        String password = null;

        try{
            user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            username = user.getUsername();
            password = user.getPassword();
        }catch (StreamReadException e){
            e.printStackTrace();
        }catch (DatabindException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException{
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)authResult.getPrincipal();
        String username = user.getUsername();
        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();
        List<String> roleNames = roles.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        Claims claims = Jwts.claims()
                .add("authorities",roleNames)
                .add("username",username)
                .build();

        String token = Jwts.builder()
                .subject(username)
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .issuedAt(new Date())
                .signWith(SECRET_KEY)
                .compact();

        response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + token);
        Map<String, String> body = new HashMap<>();
        body.put("token", token);
        body.put("username", username);
        body.put("message", String.format("Hola %s has iniciado sesión con exito", username));


        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setContentType(Content_Type);
        response.setStatus(200);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException{
        Map<String, String> body = new HashMap<>();
        if (failed instanceof DisabledException) {
            body.put("message", "El usuario está deshabilitado. Contacta al administrador.");
        }
        else if (failed instanceof BadCredentialsException) {
            body.put("message", "Username o password incorrectos.");
        }
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType(Content_Type);
    }
}
