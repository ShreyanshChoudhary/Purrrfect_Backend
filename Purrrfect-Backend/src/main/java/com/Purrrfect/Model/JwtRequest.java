package com.Purrrfect.Model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class JwtRequest {

    private String userName;   // Changed from email to userName to align with login credentials
    private String password;   // Add password field to store the user's password

}
