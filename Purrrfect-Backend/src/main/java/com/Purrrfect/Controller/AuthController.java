package com.Purrrfect.Controller;


import com.Purrrfect.DTO.SignupRequest;
import com.Purrrfect.Model.JwtRequest;
import com.Purrrfect.Model.JwtResponse;
import com.Purrrfect.Model.User;
import com.Purrrfect.Service.UserService;
import com.Purrrfect.Security.JwtHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtHelper helper;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
        // Authenticate the user
        this.doAuthenticate(request.getUserName(), request.getPassword());

        // Load user details from the database
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUserName());

        // Generate a JWT token
        String token = this.helper.generateToken(userDetails);

        // Create the response containing the JWT token and the username
        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .username(userDetails.getUsername())
                .build();

        // Return the response with HTTP status 200 (OK)
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Signup endpoint
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest signupRequest) {
        // Check if the user already exists
        User existingUser = userService.getUserByUsername(signupRequest.getUsername());
        if (existingUser != null) {
            return new ResponseEntity<>("Username already taken", HttpStatus.BAD_REQUEST);
        }

        // Create a new user
        User newUser = new User();
        newUser.setUsername(signupRequest.getUsername());
        newUser.setPassword(signupRequest.getPassword());  // You should hash the password before saving
        newUser.setEmail(signupRequest.getEmail());
        newUser.setPhoneNumber(signupRequest.getPhoneNumber());

        // Save the user to the database
        userService.addUser(newUser);

        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }

    // Authenticate the user using username and password
    private void doAuthenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
        try {
            manager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid Username or Password!!");
        }
    }

    // Exception handler for BadCredentialsException
    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Invalid Username or Password!!";
    }
}
