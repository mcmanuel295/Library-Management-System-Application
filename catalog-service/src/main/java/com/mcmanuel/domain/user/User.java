package com.mcmanuel.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String lastname;

    private String firstname;

    private String fullName ;

    private String email;

    private String password;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    private boolean enabled;

    private boolean accountLocked;



}
