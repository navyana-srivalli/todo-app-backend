package com.example.Todoapp.controller;

import com.example.Todoapp.dto.TodoDto;
import com.example.Todoapp.entity.Todo;
import com.example.Todoapp.mapper.TodoMapper;
import com.example.Todoapp.repository.TodoRepository;
import com.example.Todoapp.request.TodoRequest;
import com.example.Todoapp.service.CurrentUserContext;
import com.example.Todoapp.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TodoController {

    private  final  TodoRepository todoRepository;
    private final  TodoService todoService;
    private final TodoMapper todoMapper;

    @GetMapping
    public List<TodoDto> list(){
        String user = CurrentUserContext.getCurrentUser();
        return todoMapper.mapDTO(todoService.getTodos());
    }

    @PostMapping
    public List<TodoDto> create(@RequestBody TodoRequest request) throws Exception{
        Todo todo = todoMapper.map(request);
        todoService.save(todo);
        return todoMapper.mapDTO(todoService.getTodos());
    }

    @DeleteMapping("/{id}")
    public void deleteTodoById(@PathVariable("id") Long id) {
        todoService.delete(id);
    }

    @PutMapping("/{id}")
    public List<TodoDto> update(@PathVariable int id, @RequestBody TodoRequest request) throws Exception{
        Todo todo = todoMapper.map(request);
        todoService.update(todo);
        return todoMapper.mapDTO(todoService.getTodos());
    }

}