package com.blog.app.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.app.blog.payloads.CategoryDto;
import com.blog.app.blog.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    public CategoryService categoryService;

    //ceate category
    @PostMapping("/")
    public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto cat =  this.categoryService.addCategory(categoryDto);
        return new ResponseEntity<>(cat, HttpStatus.CREATED);
    }

    //update category
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable("categoryId") int categoryId) {
        CategoryDto cat =  this.categoryService.updateCategory(categoryDto, categoryId);
        return new ResponseEntity<>(cat, HttpStatus.OK);
    }

    //delete category
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") int categoryId) {
        this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //get category
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("categoryId") int categoryId) {
        CategoryDto cat =  this.categoryService.getCategory(categoryId);
        return new ResponseEntity<>(cat, HttpStatus.OK);
    }

    //get all categories
    @GetMapping("/")
    public ResponseEntity<?> getAllCategory() {
        return new ResponseEntity<>(this.categoryService.getAllCategory(), HttpStatus.OK);
    }

    
}
