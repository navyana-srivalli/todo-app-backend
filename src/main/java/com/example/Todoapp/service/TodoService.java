package com.example.Todoapp.service;

import com.example.Todoapp.entity.Todo;
import com.example.Todoapp.entity.User;
import com.example.Todoapp.filter.JwtTokenFilter;
import com.example.Todoapp.repository.TodoRepository;
import com.example.Todoapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService{
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    @Autowired
    JwtTokenFilter jwtTokenFilter;

    public TodoService(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }


    public void delete(Long id) {
        todoRepository.deleteTodoById(id);
    }

    public List<Todo> getTodos() {
        String username = CurrentUserContext.getCurrentUser();
        User user = userRepository.getUserByUsername(username);
        return user.getTodos();
    }

    public void update(Todo updatedTodo) throws Exception {
            save(updatedTodo);
    }


    public void save(Todo todo) throws Exception {
        String user = CurrentUserContext.getCurrentUser();
        Optional<User> userOptional = Optional.ofNullable(userRepository.getUserByUsername(user));
        if(((Optional<?>) userOptional).isEmpty()) {
            throw new Exception("User Not found");
        }
        User userData = userOptional.get();
        todo.setUser(userData);
        todoRepository.save(todo);
    }

}