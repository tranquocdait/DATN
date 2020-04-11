package com.tranquocdai.freshmarket.repository;

import com.tranquocdai.freshmarket.model.Post;
import com.tranquocdai.freshmarket.model.RatePost;
import com.tranquocdai.freshmarket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface RatePostRepository extends JpaRepository<RatePost,Long> {
    List<RatePost> findByPost(Post post);
    Optional<RatePost> findByIdAndUser(Long id, User user);
    Optional<RatePost> findByPostAndUser(Post post, User user);
}
