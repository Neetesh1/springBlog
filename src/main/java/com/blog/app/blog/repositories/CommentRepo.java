package com.blog.app.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.app.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
    
}
