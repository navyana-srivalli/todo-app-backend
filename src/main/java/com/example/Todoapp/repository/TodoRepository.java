package com.example.Todoapp.repository;

import com.example.Todoapp.entity.Todo;
import com.example.Todoapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface TodoRepository extends JpaRepository<Todo,Integer> {
    void deleteTodoById(int id);
    Todo getTodoById(int id);
    List<Todo> getTodoByUser(User user);

}