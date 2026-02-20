package com.formation.blogapp.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "comments")
public class Comment extends TraceableEntity{

    @Column(length = 255)
    private String content;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "blog_id", nullable = false)
    private BlogPost blog;

}
