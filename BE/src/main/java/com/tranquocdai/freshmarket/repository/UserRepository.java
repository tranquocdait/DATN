package com.tranquocdai.freshmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tranquocdai.freshmarket.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String username);
    List<User> findByFullNameContains(String keySearch);

    @Query(value = "SELECT * FROM freshmarket.user where role_user_id!=1 ORDER BY full_name DESC;",nativeQuery = true)
    List<User> findUsers();

    @Query(value = "SELECT * FROM user WHERE full_name LIKE ?1 and role_user_id!=1 ORDER BY full_name DESC;",nativeQuery = true)
    List<User> findSearchUsers(String search);
}
