package com.tranquocdai.freshmarket.repository;

import com.tranquocdai.freshmarket.model.StatusPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusPostRepository extends JpaRepository<StatusPost,Long> {
}
