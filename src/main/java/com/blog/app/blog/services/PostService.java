package com.blog.app.blog.services;

import com.blog.app.blog.payloads.PostDto;
import com.blog.app.blog.payloads.PostResponse;

public interface PostService {

    //create post
    PostDto CreatePost(PostDto postDto, int userId, int categoryId);

    //update post
    PostDto UpdatePost(PostDto postDto, int postId);

    //delete post
    void DeletePost(int postId);

    //get post
    PostDto GetPostById(int postId);

    //get all posts
    PostResponse GetAllPosts(int pgNo, int pgSize, String sortBy, String sortDir);

    //get all posts by category
    PostResponse GetAllPostsByCategory(int categoryId, int pgNo, int pgSize);

    //get all posts by user
    PostResponse GetAllPostsByUser(int userId, int pgNo, int pgSize);

    //seach post by keyword
    PostResponse SearchPostByKeyword(String keyword, int pgNo, int pgSize, String sortBy, String sortDir);
    
}
