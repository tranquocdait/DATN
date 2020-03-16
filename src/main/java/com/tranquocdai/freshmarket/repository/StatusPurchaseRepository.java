package com.tranquocdai.freshmarket.repository;

import com.tranquocdai.freshmarket.model.StatusPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusPurchaseRepository extends JpaRepository<StatusPurchase,Long> {
}
