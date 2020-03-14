package com.tranquocdai.freshmarket.repository;

import com.tranquocdai.freshmarket.model.Post;
import com.tranquocdai.freshmarket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    Collection<Post> findByUser(User user);
}
