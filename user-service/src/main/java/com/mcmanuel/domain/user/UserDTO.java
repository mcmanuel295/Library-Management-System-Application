package com.mcmanuel.domain.user;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Builder
@Data
public class UserDTO {
    private UUID userId;

//    @NotBlank(message = "this field cannot be blank")
//    private String lastname;
//
//    @NotBlank(message = "this field cannot be blank")
//    private String firstname;

    @NotBlank(message = "this field cannot be blank")
    private String fullName;

    @Column(unique = true,nullable = false)
    @Email
    private String email;
    private ArrayList<Role> roles;
    @Column(nullable = false,updatable = false,insertable = false)
    private LocalDateTime createdDate;

}
