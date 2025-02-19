package com.example.SimpleMMO.repository;

import com.example.SimpleMMO.entity.MMOUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MMOUserRepository extends JpaRepository<MMOUser, Long> {

    Optional<MMOUser> findByUsername(String username);
    Optional<MMOUser> findByEmail(String email);
}
