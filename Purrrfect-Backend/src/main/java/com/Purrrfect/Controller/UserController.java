package com.Purrrfect.Controller;

import com.Purrrfect.Model.User;
import com.Purrrfect.Service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * Sign up a new user.
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody User user) {
        logger.info("Attempting to sign up user with username {}", user.getUsername());

        // Check for existing user based on username
        if (userService.getUserByUsername(user.getUsername()) != null) {
            logger.error("User already exists with username: {}", user.getUsername());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists with the username: " + user.getUsername());
        }

        userService.addUser(user);
        logger.info("User signed up successfully with username {}", user.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body("User signed up successfully");
    }

    /**
     * Login: Get the username of the currently logged-in user.
     */
    @GetMapping("/current-user")
    public ResponseEntity<String> getLoggedInUser(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No user is currently logged in");
        }
        return ResponseEntity.ok(principal.getName());
    }
}
