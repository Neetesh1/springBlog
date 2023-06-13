package com.blog.app.blog.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter  extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
            
        //get the token from the header
        String authorizationHeader = request.getHeader("Authorization");
        System.out.println("authorizationHeader: " + authorizationHeader);
        String token = null;
        String username = null; 
        // JWT Token is in the form "Bearer token". Remove Bearer word and get
        // only the Token
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            try {
                username = jwtTokenHelper.getUsernameFromToken(token);
            }
            catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            }
            catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }
            catch(MalformedJwtException e){
                System.out.println("JWT Token is malformed");
            }
            
        }else{
            System.out.println("Token not found or not start with Bearer");
        }

        //validate the token
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
             //get the user details
             var userDetails = userDetailsService.loadUserByUsername(username);
            if(!jwtTokenHelper.validateToken(token, userDetails)){
                System.out.println("Token is not valid");
                //return;
            }else{
                //create the authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //set the authentication in the context
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
           
            
        }else{
            System.out.println("Token is not valid");
        }

        filterChain.doFilter(request, response);
        
    }
    
}
