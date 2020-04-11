package com.tranquocdai.freshmarket.repository;

import com.tranquocdai.freshmarket.model.Post;
import com.tranquocdai.freshmarket.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase,Long> {
    Collection<Purchase> findAllByPost(Post post);
    Optional<Purchase> findById(Long id);
}
