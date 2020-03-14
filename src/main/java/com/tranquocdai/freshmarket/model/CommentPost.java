package com.tranquocdai.freshmarket.model;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CommentPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(targetEntity = User.class)
    private User user;

    @ManyToOne(targetEntity = Post.class)
    private Post post;

    private LocalDateTime dateOfComment;

    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public LocalDateTime getDateOfComment() {
        return dateOfComment;
    }

    public void setDateOfComment(LocalDateTime dateOfComment) {
        this.dateOfComment = dateOfComment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
