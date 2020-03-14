package com.tranquocdai.freshmarket.repository;

import com.tranquocdai.freshmarket.model.CalculationUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalculationUnitRepository extends JpaRepository<CalculationUnit,Long> {
}
