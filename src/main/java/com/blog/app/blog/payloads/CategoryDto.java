package com.blog.app.blog.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    
    private int categoryId;

    @NotEmpty
    @Size(min = 4, message = "Category title must be at least 4 characters long")
    private String categoryTitle;

    @NotEmpty
    @Size(min = 10, message = "Category description must be at least 10 characters long")
    private String categoryDescription;


}
