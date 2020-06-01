package com.tranquocdai.freshmarket.repository;

import com.tranquocdai.freshmarket.model.Post;
import com.tranquocdai.freshmarket.model.Purchase;
import com.tranquocdai.freshmarket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase,Long> {
    List<Purchase> findByPost(Post post);
    Optional<Purchase> findById(Long id);
    List<Purchase> findByBuyer(User user);
    @Query(value = "SELECT id FROM purchase WHERE post_id= :postId and status_purchase_id <> 4 ;",nativeQuery = true)
    List<Purchase> findPurchaseNotCancel(@Param("postId") Long postId);
    @Query(value = "SELECT id FROM purchase WHERE post_id = :postId and status_purchase_id=4 ;",nativeQuery = true)
    List<Purchase> findPurchaseCancel(@Param("postId") Long postId);
}
