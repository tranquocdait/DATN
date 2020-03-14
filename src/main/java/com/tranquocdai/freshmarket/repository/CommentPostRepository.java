package com.tranquocdai.freshmarket.repository;

import com.tranquocdai.freshmarket.model.CommentPost;
import com.tranquocdai.freshmarket.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CommentPostRepository extends JpaRepository<CommentPost,Long> {
    Collection<CommentPost> findByPost(Post post);
}
