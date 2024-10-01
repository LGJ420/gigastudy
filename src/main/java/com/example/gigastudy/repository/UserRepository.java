package com.example.gigastudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gigastudy.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
}
