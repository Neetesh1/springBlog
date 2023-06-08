package com.blog.app.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.app.blog.payloads.ApiResponse;
import com.blog.app.blog.payloads.Userdto;
import com.blog.app.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    // POST-create
    @PostMapping("/")
    public ResponseEntity<Userdto> createuser(@Valid @RequestBody Userdto userdto) {
        Userdto createdUserDto = userService.createuser(userdto);
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }

    // PUT update user
    @PutMapping("/{userId}")
    public ResponseEntity<Userdto> updateUser(@Valid @RequestBody Userdto userdto, @PathVariable("userId") Integer userId) {
        Userdto updatedUserDto = userService.updateuser(userdto, userId);
        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }

    // DELETE - delete user
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteuser(@PathVariable("userId") Integer userId) {

        this.userService.deleteuser(userId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("user dleted successfully", true), HttpStatus.OK);

    }

    // GET-get list of users
    @GetMapping("/")
    public ResponseEntity<List<Userdto>> getAllUsers() {
        return ResponseEntity.ok(this.userService.getallusers());
    }

    // GET-get single user
    @GetMapping("/{userId}")
    public ResponseEntity<Userdto> getSingleUser(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(this.userService.getuserById(userId));
    }
}
