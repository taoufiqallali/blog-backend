package com.formation.blogapp.repository;

import com.formation.blogapp.domain.BlogPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long>, BlogPostRepositoryCustom {

    public boolean existsBySlug(String slug);

    public Page<BlogPost> findByAuthorEmail(String authorEmail, Pageable pageable);

    public Optional<BlogPost> findByIdAndAuthorEmail(Long id, String authorEmail);

}
