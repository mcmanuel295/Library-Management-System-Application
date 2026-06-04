package com.mcmanuel.domain.user;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public record UserDTO(
        UUID userId,
        //    String lastname,
        //    String firstname,

        @NotBlank(message = "this field cannot be blank") String fullName,
        @Column(unique = true, nullable = false) @Email String email,
        ArrayList<Role> roles,
        @Column(nullable = false, updatable = false, insertable = false) LocalDateTime createdDate) {}
