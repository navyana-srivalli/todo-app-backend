package com.example.Todoapp.repository;

import com.example.Todoapp.entity.Todo;
import com.example.Todoapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface TodoRepository extends JpaRepository<Todo,Integer> {
    @Transactional
    void deleteTodoById(Long id);
    Todo getTodoById(int id);

}