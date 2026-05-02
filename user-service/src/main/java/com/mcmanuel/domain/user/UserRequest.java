package com.mcmanuel.domain.user;

import lombok.Data;

@Data
public class UserRequest {
    private String lastname;
    private String firstname;
    private String email;
    private String password;

}
