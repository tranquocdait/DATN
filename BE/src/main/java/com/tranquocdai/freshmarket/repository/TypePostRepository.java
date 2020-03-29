package com.tranquocdai.freshmarket.repository;

import com.tranquocdai.freshmarket.model.TypePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypePostRepository extends JpaRepository<TypePost,Long> {
}
