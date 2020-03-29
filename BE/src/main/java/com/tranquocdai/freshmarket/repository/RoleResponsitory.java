package com.tranquocdai.freshmarket.repository;

import com.tranquocdai.freshmarket.model.RoleUser;
import com.tranquocdai.freshmarket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleResponsitory extends JpaRepository<RoleUser,Long> {
    List<RoleUser> findAll();
    Optional<RoleUser> findByRoleID(Long id);
}
