package com.blog.app.blog.services;

import com.blog.app.blog.payloads.CommentDto;

public interface CommentSerivce {
    
    CommentDto createComment(CommentDto commentDto, Integer postId);

    void deleteComment(Integer commentId);
}
