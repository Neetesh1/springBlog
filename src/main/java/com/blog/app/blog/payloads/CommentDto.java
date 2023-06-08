package com.blog.app.blog.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
   
    public static final String HttpStatus = null;

    private int id;

    private String content;
}
