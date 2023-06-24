package com.blog.app.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Userdto {
    
    private int id;

    @NotEmpty
    @Size(min = 4, message = "name must be atleast 4 characters long")
    private String name;

    @Email(message = "email must be valid")
    private String email;

    @NotEmpty
    @Size(min = 3, max = 10,  message = "password must be minimum of 3 characters  and maximum of 10 characters long")
    private String password;

    @NotEmpty
    private String about;

     private Set<RoleDto> roles = new HashSet<>();
}
