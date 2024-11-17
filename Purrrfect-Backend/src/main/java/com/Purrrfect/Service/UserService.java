package com.Purrrfect.Service;

import com.Purrrfect.Model.User;
import com.Purrrfect.Repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepo repo;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepo repo) {
        this.repo = repo;
    }

    // Fetch all users - Optional for admin or debugging purposes (not required for signup/login)
    public List<User> getUsers() {
        logger.info("Fetching all users");
        return repo.findAll();
    }

    // Fetch a user by userId - Can be useful for login (fetch user based on some unique identifier)
    public User getUserById(Integer userId) {
        return repo.findById(userId).orElseGet(() -> {
            logger.warn("User with userId - {} not found", userId);
            return null;  // No default user created, just return null
        });
    }

    // Add a new user for signup
    public void addUser(User user) {
        user.setUserId(null);
        // Ensure that the userId is not sent in the request (it will be auto-generated)
        repo.save(user);
        logger.info("User with userId - {} added successfully", user.getUserId());
    }

    // Method to check if a user already exists by username - Useful for signup validation
    public User getUserByUsername(String username) {
        return repo.findByUsername(username).orElse(null);  // Return null if user not found
    }

    // Method to check if a user exists by email - Useful for login validation
    public User getUserByEmail(String email) {
        return repo.findByEmail(email).orElse(null);  // Return null if user not found
    }
}
