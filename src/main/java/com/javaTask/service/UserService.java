package com.javaTask.service;

import com.javaTask.model.User;
import com.javaTask.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Aleksandr Beryozkin
 */

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User findByUsername(String username) {
        return userRepository.findOneByUsername(username);
    }
 }
