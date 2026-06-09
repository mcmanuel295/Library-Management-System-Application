package com.mcmanuel.domain.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import lombok.*;
import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Builder
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @Column(nullable = false)
    @NotBlank(message = "this field cannot be blank") private String lastname;

    @Column(nullable = false)
    @NotBlank(message = "this field cannot be blank") private String firstname;

    @Column(nullable = false)
    @NotBlank(message = "this field cannot be blank") private String fullName = getFullName();

    @Email @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "this field cannot be blank") private String password;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    private boolean enabled;

    private boolean accountLocked;

    private ArrayList<Role> roles;

    public void setFullName() {
        this.fullName = this.lastname + " " + this.firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
        setFullName();
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
        setFullName();
    }
}
