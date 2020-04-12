package com.tranquocdai.freshmarket.repository;

import com.tranquocdai.freshmarket.model.CommentPost;
import com.tranquocdai.freshmarket.model.Post;
import com.tranquocdai.freshmarket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface CommentPostRepository extends JpaRepository<CommentPost,Long> {
    Collection<CommentPost> findByPost(Post post);
    Optional<CommentPost> findByIdAndUser(Long id, User user);
    Optional<CommentPost> findByPostAndUser(Post post, User user);
}
