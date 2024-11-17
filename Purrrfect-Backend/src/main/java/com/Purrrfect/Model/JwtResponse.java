package com.Purrrfect.Model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class JwtResponse {

    private String jwtToken;  // Field to store the JWT token
    private String username;  // Field to store the username
}
