package com.mcmanuel.domain.user.request;

import lombok.Data;

@Data
public class LoginRequest {
    String email;
    String password;
}

