package com.tranquocdai.freshmarket.repository;

import com.tranquocdai.freshmarket.model.TransportationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportationTypeRepository extends JpaRepository<TransportationType,Long> {
}
