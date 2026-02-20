package com.formation.blogapp.repository;

import com.formation.blogapp.domain.BlogPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogPostRepositoryCustom {

    Page<BlogPost> searchPosts(String keyword, Pageable pageable);


}
