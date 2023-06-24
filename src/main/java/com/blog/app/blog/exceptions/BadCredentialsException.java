package com.blog.app.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadCredentialsException extends RuntimeException {

    String username;
    String password;

    public BadCredentialsException() {
        super();
    }

    public BadCredentialsException(String username, String password) {
        super( String.format("User does not exist with  username: %s  and password: %s ", username, password) );
        this.username = username;
        this.password = password;
        
    }
    
}
