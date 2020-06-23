package com.tranquocdai.freshmarket.repository;

import com.tranquocdai.freshmarket.model.Category;
import com.tranquocdai.freshmarket.model.Post;
import com.tranquocdai.freshmarket.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByUser(User user);
    Optional<Post> findByUserAndId(User user,Long id);
    List<Post> findByCategory(Category category);
    List<Post> findByPostNameContains(String keySearch);
    @Query(value = "SELECT * FROM posts where status_post_id=1;",nativeQuery = true)
    List<Post> findPostActive();
}
