package com.blog.app.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.app.blog.entities.User;
import com.blog.app.blog.exceptions.ResourceNotFoundException;
import com.blog.app.blog.repositories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       //loading user from database by username
       System.out.println("email id is ===="+username);
        User user = this.userRepo.findByEmail(username)
            .orElseThrow(() -> new ResourceNotFoundException("user","email",username));
            
        return user;

       
    }
    
}
