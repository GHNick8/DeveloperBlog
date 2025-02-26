package com.project.blog.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.blog.model.BlogPost;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long>{
    // Search posts containing the given keyword in the title
    List<BlogPost> findByTitleContaining(String keyword); 

    // Filter posts by tags 
    List<BlogPost> findByTagsIn(List<String> tags);

    // Search posts containing the given keyword in the title with pagination support
    Page<BlogPost> findByTitleContaining(String keyword, Pageable pageable);
}
