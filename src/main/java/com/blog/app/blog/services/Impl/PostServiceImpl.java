package com.blog.app.blog.services.Impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.app.blog.entities.Category;
import com.blog.app.blog.entities.Post;
import com.blog.app.blog.entities.User;
import com.blog.app.blog.exceptions.ResourceNotFoundException;
import com.blog.app.blog.payloads.PostDto;
import com.blog.app.blog.payloads.PostResponse;
import com.blog.app.blog.repositories.CategoryRepo;
import com.blog.app.blog.repositories.PostRepo;
import com.blog.app.blog.repositories.UserRepo;
import com.blog.app.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public PostDto CreatePost(PostDto postDto, int userId, int categoryId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user_id", userId));

        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category_id", categoryId));

        Post post = this.modelMapper.map(postDto, Post.class);

        post.setPostImage("default.jpg");
        post.setPostDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post addedPost = this.postRepo.save(post);
        return this.modelMapper.map(addedPost, PostDto.class);
    }

    @Override
    public void DeletePost(int postId) {

        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post_id", postId));
        this.postRepo.delete(post);

    }

    @Override
    public PostResponse GetAllPosts(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
         
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Post> pagePost = this.postRepo.findAll(pageable);

        List<Post> posts = pagePost.getContent();

        List<PostDto> postDto = posts.stream().map( (post) -> modelMapper.map(post, PostDto.class) )
                .collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDto);
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setTotalElements(pagePost.getTotalElements());

        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }

    @Override
    public PostResponse GetAllPostsByCategory(int categoryId, int pageNumber, int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Category cat = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category_id", categoryId));
        Page<Post> pagePost = this.postRepo.findByCategory(cat, pageable);
        List<Post>  posts = pagePost.getContent();
        //System.out.println(posts);
        List<PostDto> allPostDto = posts.stream().map((post) -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(allPostDto);
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());


        return postResponse;
    }

    @Override
    public PostResponse GetAllPostsByUser(int userId, int pageNumber, int pageSize) {
        
        User user = this.userRepo.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User", "user_id", userId));

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Post> pagePost= this.postRepo.findByUser(user, pageable);
        List<Post> posts = pagePost.getContent();
        List<PostDto> allPostDto = posts.stream().map((post) -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(allPostDto);
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }

    @Override
    public PostDto GetPostById(int postId) {
        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post_id", postId));
        PostDto postDto = this.modelMapper.map(post, PostDto.class);
        return postDto;
    }

    @Override
    public PostResponse SearchPostByKeyword(String keyword, int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Post> pagePost = this.postRepo.findByPostTitleOrPostContentContaining(keyword, keyword, pageable);

        List<Post> posts = pagePost.getContent();
        
        List<PostDto> PotsDto = posts.stream().map( (post) -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(PotsDto);
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
}

    @Override
    public PostDto UpdatePost(PostDto postDto, int postId) {
        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post_id", postId));
        post.setPostTitle(postDto.getPostTitle());
        post.setPostContent(postDto.getPostContent());
        post.setPostImage(postDto.getPostImage());
        Post updatedPost = this.postRepo.save(post);
        return this.modelMapper.map(updatedPost, PostDto.class);
       
    }



}
 