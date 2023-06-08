package com.blog.app.blog.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.app.blog.entities.Category;
import com.blog.app.blog.entities.Post;
import com.blog.app.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {

    Page<Post> findByCategory(Category category, Pageable pageable);
    Page<Post> findByUser(User user, Pageable pageable);
    Page<Post> findByPostTitleOrPostContentContaining(String postTitle, String postContent, Pageable pageable);
    List<Post> findByPostTitleContaining(String postTitle);
    
}
