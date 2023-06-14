package com.example.Todoapp.repository;

import com.example.Todoapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User getUserByUsername(String username);
    User getUserById(int id);
}
