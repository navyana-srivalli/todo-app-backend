package com.example.Todoapp.service;

import com.example.Todoapp.entity.User;
import com.example.Todoapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public void saveUser(User user) {
        userRepository.save(user);
    }
//    public void deleteUser(User user) {
//        userRepository.deleteById(user.getId());
//    }

//    public User find(String username) throws Exception {
//        Optional<User> optionalUser = Optional.ofNullable(userRepository.getUserByUsername(username));
//        if (optionalUser.isPresent()) {
//            throw new Exception("User already exists");
//        }
//        return null;
//    }

    public void checkIfUserExist(String username) throws Exception{
        Optional<User> optionalUser = Optional.ofNullable(userRepository.getUserByUsername(username));
        if (optionalUser.isPresent()) {
            throw new Exception("User already exists");
        }
    }
}
