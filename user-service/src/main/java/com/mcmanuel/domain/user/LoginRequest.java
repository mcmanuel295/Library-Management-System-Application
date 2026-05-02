package com.mcmanuel.domain.user;

import lombok.Data;

@Data
public class LoginRequest {
    String email;
    String password;
}

