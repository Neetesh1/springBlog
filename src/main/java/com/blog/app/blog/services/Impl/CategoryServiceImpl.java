package com.blog.app.blog.services.Impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.app.blog.entities.Category;
import com.blog.app.blog.exceptions.ResourceNotFoundException;
import com.blog.app.blog.payloads.CategoryDto;
import com.blog.app.blog.repositories.CategoryRepo;
import com.blog.app.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
    
    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {

        Category category = modelMapper.map(categoryDto, Category.class);
        Category addedCategory =  this.categoryRepo.save(category);
        return modelMapper.map(addedCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategory(int categoryId) {
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category_id", categoryId));
        this.categoryRepo.delete(category);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories = this.categoryRepo.findAll();
        return categories.stream().map(category -> modelMapper.map(category, CategoryDto.class)).toList();
    }

    @Override
    public CategoryDto getCategory(int categoryId) {
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category_id", categoryId));
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, int categoryId) {
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category_id", categoryId));
        
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCategory = this.categoryRepo.save(category);
        
        return modelMapper.map(updatedCategory, CategoryDto.class);
    }
    
}
