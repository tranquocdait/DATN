package com.tranquocdai.freshmarket.repository;

import com.tranquocdai.freshmarket.model.ImagePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagePostRepository extends JpaRepository<ImagePost,Long> {
}
