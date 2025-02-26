package com.project.blog.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog.model.BlogPost;
import com.project.blog.service.BlogPostService;

@RestController
@RequestMapping("/api/posts")
public class BlogPostController {
    
    private final BlogPostService blogPostService;

    public BlogPostController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    // Retrieve all posts
    @GetMapping
    public List<BlogPost> getAllPosts() {
        return blogPostService.getAllPosts();
    }

    // Retrieve a specific post by ID
    @GetMapping("/{id}")
    public ResponseEntity<BlogPost> getPostByIdResponseEntity(@PathVariable Long id) {
        return blogPostService.getPostById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

    // Create a new post (admin-only endpoint)
    @PostMapping
    public BlogPost createPost(@RequestBody BlogPost blogPost) {
        return blogPostService.createPost(blogPost);
    }

    // Update an existing post (admin-only endpoint)
    @PutMapping("/{id}")
    public BlogPost updatePost(@PathVariable Long id, @RequestBody BlogPost blogPost) {
        return blogPostService.updatePost(id, blogPost);
    }

    // Delete a post (admin-only endpoint)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        blogPostService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint for paginated and sorted blog posts
    @GetMapping("/paginated")
    public Page<BlogPost> getPaginatedPosts(Pageable pageable) {
        return blogPostService.getPaginatedPosts(pageable);
    }
    
    // Search posts by title keyword & search with pagination 
    @GetMapping("/search")
    public Page<BlogPost> searchPosts(@RequestParam String keyword, Pageable pageable) {
        return blogPostService.searchByTitle(keyword, pageable);
    }
}
