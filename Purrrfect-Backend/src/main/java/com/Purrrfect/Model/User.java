package com.Purrrfect.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId; // The primary key is userId

    @NotNull(message = "User name cannot be null")
    @Size(min = 2, max = 100, message = "User name must be between 2 and 100 characters")
    @Column(nullable = false,name = "user_name",unique = true, length = 100)
    private String username;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @ToString.Exclude
    @Column(nullable = false)
    private String password;

    @NotNull(message = "Phone number cannot be null")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    @Column(nullable = false, length = 10)
    private String phoneNumber;

    @Size(max = 255, message = "Address must be less than 255 characters")
    private String address;

    private LocalDate accountCreatedDate;

    private Boolean isActive;

    @PrePersist
    public void prePersist() {
        if (this.accountCreatedDate == null) {
            this.accountCreatedDate = LocalDate.now();
        }
        if (this.isActive == null) {
            this.isActive = true; // Default value for isActive field
        }
    }
}
