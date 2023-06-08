package com.blog.app.blog.services;

import java.util.List;

import com.blog.app.blog.payloads.CategoryDto;

public interface CategoryService {

    CategoryDto addCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto, int categoryId);
    void deleteCategory(int categoryId);
    CategoryDto getCategory(int categoryId);
    List<CategoryDto> getAllCategory();
   
    
}
