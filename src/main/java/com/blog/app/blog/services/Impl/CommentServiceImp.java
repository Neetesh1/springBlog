package com.blog.app.blog.services.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.app.blog.entities.Comment;
import com.blog.app.blog.entities.Post;
import com.blog.app.blog.exceptions.ResourceNotFoundException;
import com.blog.app.blog.payloads.CommentDto;
import com.blog.app.blog.repositories.CommentRepo;
import com.blog.app.blog.repositories.PostRepo;
import com.blog.app.blog.services.CommentSerivce;

@Service
public class CommentServiceImp implements CommentSerivce {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {

        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post_id", postId));
        
        Comment comment = this.modelMapper.map(commentDto, Comment.class);

        comment.setPost(post);

        Comment savedComment = this.commentRepo.save(comment);
        
        CommentDto savedCommentDto = this.modelMapper.map(savedComment, CommentDto.class);

        return savedCommentDto;
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "comment_id", commentId));
        
        this.commentRepo.delete(comment);
        
    }
    
}
