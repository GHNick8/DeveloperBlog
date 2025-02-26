package com.project.blog.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.blog.model.BlogPost;
import com.project.blog.service.BlogPostService;

@Controller
public class HomeController {
    private final BlogPostService blogPostService;

    public HomeController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<BlogPost> blogPosts = blogPostService.getAllPosts();
        model.addAttribute("blogPosts", blogPosts);
        return "index";
    }
}
