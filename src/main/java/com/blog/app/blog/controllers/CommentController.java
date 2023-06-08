package com.blog.app.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.app.blog.payloads.ApiResponse;
import com.blog.app.blog.payloads.CommentDto;
import com.blog.app.blog.services.CommentSerivce;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    @Autowired
    private CommentSerivce commentSerivce;

    @PostMapping("/post/{postId}")
    public ResponseEntity<CommentDto> createComment(
        @PathVariable("postId") int postId,
        @RequestBody CommentDto commentDto
    ){

        CommentDto comment = this.commentSerivce.createComment(commentDto, postId);

        return new ResponseEntity<CommentDto>(comment, HttpStatus.CREATED);

    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(
        @PathVariable("commentId") int commentId
        
    ){

        this.commentSerivce.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("comment deleted successfuly", true), HttpStatus.OK);

    }

    
}
