package com.project.blog.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.blog.model.BlogPost;
import com.project.blog.repository.BlogPostRepository;

@Service
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;

    public BlogPostService(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    public List<BlogPost> getAllPosts() {
        return blogPostRepository.findAll();
    }

    public Optional<BlogPost> getPostById(Long id) {
        return blogPostRepository.findById(id);
    }

    public BlogPost createPost(BlogPost blogPost) {
        blogPost.setPubDate(LocalDateTime.now());
        return blogPostRepository.save(blogPost);
    }

    public BlogPost updatePost(Long id, BlogPost updatePost) {
        return blogPostRepository.findById(id)
        .map(post -> {
            post.setTitle(updatePost.getTitle());
            post.setContent(updatePost.getContent());
            post.setTags(updatePost.getTags());
            return blogPostRepository.save(post);
        })
        .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public void deletePost(Long id) {
        blogPostRepository.deleteById(id);
    }

    public List<BlogPost> searchByTitle(String keyword) {
        return blogPostRepository.findByTitleContaining(keyword);
    }

    public Page<BlogPost> getPaginatedPosts(Pageable pageable) {
        return blogPostRepository.findAll(pageable);
    }

    public Page<BlogPost> searchByTitle(String keyword, Pageable pageable) {
        return blogPostRepository.findByTitleContaining(keyword, pageable);
    }
}
