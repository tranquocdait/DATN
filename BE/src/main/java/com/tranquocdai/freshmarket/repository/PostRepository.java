package com.tranquocdai.freshmarket.repository;

import com.tranquocdai.freshmarket.model.Post;
import com.tranquocdai.freshmarket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByUser(User user);
    Optional<Post> findByUserAndId(User user,Long id);
    List<Post> findByPostNameContains(String keySearch);
}
