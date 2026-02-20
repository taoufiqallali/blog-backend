package com.formation.blogapp.repository;

import com.formation.blogapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    public boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);


}
