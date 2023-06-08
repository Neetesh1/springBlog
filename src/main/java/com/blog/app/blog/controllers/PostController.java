package com.blog.app.blog.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.app.blog.config.AppConstant;
import com.blog.app.blog.payloads.ApiResponse;
import com.blog.app.blog.payloads.PostDto;
import com.blog.app.blog.payloads.PostResponse;
import com.blog.app.blog.services.FileService;
import com.blog.app.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    //create post
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(
        @RequestBody PostDto postDto,
        @PathVariable Integer userId,
        @PathVariable Integer categoryId
        )
    {
        PostDto createPost  = this.postService.CreatePost(postDto, userId, categoryId);
        return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED); 

    }

    // get by user
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<PostResponse> getPostByUser(
        @PathVariable Integer userId,
        @RequestParam(value = "pageNumber" ,defaultValue = AppConstant.PAGE_NUMBER, required = false) int pageNumber,
        @RequestParam(value = "pageSize" ,defaultValue = AppConstant.PAGE_SIZE, required = false) int pageSize 
    ){
        System.out.println("userId: " + userId + " pageNumber: " + pageNumber + " pageSize: " + pageSize);
        PostResponse postResponse  = this.postService.GetAllPostsByUser(userId, pageNumber, pageSize);
        return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
    }

    //get by category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getPostByCategory(
        @PathVariable Integer categoryId,
        @RequestParam(value = "pageNumber" ,defaultValue = AppConstant.PAGE_NUMBER, required = false) int pageNumber,
        @RequestParam(value = "pageSize" ,defaultValue = AppConstant.PAGE_SIZE, required = false) int pageSize 
    ){
        PostResponse postResponse  = this.postService.GetAllPostsByCategory(categoryId, pageNumber, pageSize);
        return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
    }

    //get all posts
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
        @RequestParam(value = "pageNumber" ,defaultValue = AppConstant.PAGE_NUMBER, required = false) int pageNumber,
        @RequestParam(value = "pageSize" ,defaultValue = AppConstant.PAGE_SIZE, required = false) int pageSize,
        @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
        @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir
         ){
        PostResponse postResponse = this.postService.GetAllPosts(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
    }
    
    //get post by id
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(
        @PathVariable Integer postId
    ){
        PostDto post = this.postService.GetPostById(postId);
        return new ResponseEntity<PostDto>(post, HttpStatus.OK);
    }

    //search by keyword
    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<PostResponse> searchPosts(
        @PathVariable("keyword") String keyword,
        @RequestParam(value = "pageNumber" ,defaultValue = AppConstant.PAGE_NUMBER, required = false) int pageNumber,
        @RequestParam(value = "pageSize" ,defaultValue = AppConstant.PAGE_SIZE, required = false) int pageSize,
        @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
        @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir
    ){
        PostResponse postResponse =  this.postService.SearchPostByKeyword(keyword, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
    }
    
    //delete post
    @DeleteMapping("/posts/{postId}")
    public ApiResponse deletePost(
        @PathVariable Integer postId
    ){
        this.postService.DeletePost(postId);
        return new  ApiResponse("Post deleted successfully", true);
    }

    //update post
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(
        @PathVariable Integer postId,
        @RequestBody PostDto postDto
    ){
        PostDto updatedPost = this.postService.UpdatePost( postDto, postId);
        return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
    }


    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadFile(
        @RequestParam("file") MultipartFile image,
        @PathVariable Integer postId
    ) throws IOException{
        PostDto postdto = this.postService.GetPostById(postId);
        String fileName =  this.fileService.uploadImage(path, image); 
        postdto.setPostImage(fileName);
        PostDto updatePost= this.postService.UpdatePost(postdto, postId);
        return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
        
    }

    //method to serve file
    @GetMapping(value = "/post/image/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
        @PathVariable("fileName") String fileName,
        HttpServletResponse response
    ) throws FileNotFoundException, IOException {
        
        InputStream resource  = this.fileService.getResource(path, fileName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        FileCopyUtils.copy(resource, response.getOutputStream());
    }

    
}
