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

    @Transactional
    public void delete(int id) {
             todoRepository.deleteTodoById(id);
    }
    public Todo getTodoById(int id) {
        return todoRepository.getTodoById(id);
    }
    public List<Todo> getTodosByUser(String username) {
        User user = userRepository.getUserByUsername(username);
        return user.getTodos();
    }

    public void update(int id,Todo updatedTodo, Authentication auth) throws Exception {
            Todo todo = getTodoById(id);
            if (todo == null) {
                return;
            }
            todo.setTitle(updatedTodo.getTitle());
            save(todo, auth);
    }


    public void save(Todo todo, Authentication auth) throws Exception {
        Optional<User> userOptional = Optional.ofNullable(userRepository.getUserByUsername(auth.getName()));
        if(((Optional<?>) userOptional).isEmpty()) {
            throw new Exception("User Not found");
        }
        User userData = userOptional.get();
        todo.setUser(userData);
        todoRepository.save(todo);
    }
    public List<Todo> getTodosByUser(User user) {
        return todoRepository.getTodoByUser(user);
    }

}