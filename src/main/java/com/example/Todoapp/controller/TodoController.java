package com.example.Todoapp.controller;

import com.example.Todoapp.entity.Todo;
import com.example.Todoapp.repository.TodoRepository;
import com.example.Todoapp.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoController {
    @Autowired
    TodoRepository todoRepository;

    @Autowired
    TodoService todoService;

    @GetMapping
    public List<Todo> list(Authentication auth){
        return todoService.getTodosByUser(auth.getName());
    }

    @PostMapping
    public void create(@RequestBody Todo todo, Authentication auth) throws Exception{
         todoService.save(todo, auth);
    }
    @DeleteMapping("/{id}")
    public void deleteTodoById(@PathVariable("id") int id) {
        todoService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable int id, @RequestBody Todo updatedTodo, Authentication auth) throws Exception{
        todoService.update(id, updatedTodo, auth);
    }

}