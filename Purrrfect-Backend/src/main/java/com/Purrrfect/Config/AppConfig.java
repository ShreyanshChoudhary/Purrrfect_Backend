package com.Purrrfect.Config;

import com.Purrrfect.Model.User;
import com.Purrrfect.Repo.UserRepo;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@EntityScan(basePackages = "com.Purrrfect.Model")
@Configuration
public class AppConfig {

    private final UserRepo userRepo;

    // Constructor injection for UserRepo
    public AppConfig(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    // Configuring UserDetailsService for Spring Security
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                // Fetch user from database using the UserRepo
                User user = userRepo.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

                // Return the user with no authorities (roles) assigned for now
                return new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.emptyList()  // No roles assigned
                );
            }
        };
    }

    // PasswordEncoder for Spring Security
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager to manage authentication requests
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
}
