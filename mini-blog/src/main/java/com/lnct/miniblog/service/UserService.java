package com.lnct.miniblog.service;

import com.lnct.miniblog.model.User;
import com.lnct.miniblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by username
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Create a new user
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Update an existing user
    public User updateUser(String username, User userDetails) {
        User user = getUserByUsername(username);
        if (user != null) {
            user.setName(userDetails.getName()); // Set the new name
            user.setEmail(userDetails.getEmail());
            user.setBio(userDetails.getBio());
            return userRepository.save(user);
        }
        return null;
    }

    // Delete a user
    public void deleteUser(String username) {
        User user = getUserByUsername(username);
        if (user != null) {
            userRepository.delete(user);
        }
    }
}
