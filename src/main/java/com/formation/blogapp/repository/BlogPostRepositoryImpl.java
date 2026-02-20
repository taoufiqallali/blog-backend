package com.formation.blogapp.repository;

import com.formation.blogapp.domain.BlogPost;
import com.formation.blogapp.domain.QBlogPost;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BlogPostRepositoryImpl implements BlogPostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
        public Page<BlogPost> searchPosts(String keyword, Pageable pageable) {

            QBlogPost post = QBlogPost.blogPost;

            BooleanExpression predicate = null;

            if (keyword != null && !keyword.isBlank()) {
                predicate = post.title.containsIgnoreCase(keyword)
                        .or(post.content.containsIgnoreCase(keyword));
            }

            List<BlogPost> results = queryFactory
                    .selectFrom(post)
                    .where(predicate)
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

            Long total = queryFactory
                    .select(post.count())
                    .from(post)
                    .where(predicate)
                    .fetchOne();

            return new PageImpl<>(results, pageable, total);
    }
}

