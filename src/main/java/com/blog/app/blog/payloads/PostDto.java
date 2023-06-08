package com.blog.app.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.blog.app.blog.entities.Comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

    private int postId;
    
    private String postTitle;

    private String postContent;

    private String postImage;

    private Date postDate;

    private CategoryDto category;

    private Userdto user;

    private Set<CommentDto> comments = new HashSet<>();
    
    
}
