package com.mcmanuel.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mcmanuel.pojo.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @Column(nullable = false)
    @NotBlank(message = "this field cannot be blank")
    private String lastname;

    @Column(nullable = false)
    @NotBlank(message = "this field cannot be blank")
    private String firstname;

    @Column(nullable = false)
    @NotBlank(message = "this field cannot be blank")
    private String fullName ;

    @Email
    @Column(unique = true,nullable = false)
    private String email;

    @NotBlank(message = "this field cannot be blank")
    private String password;

    @CreatedDate
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(nullable = false,insertable = false )
    private LocalDateTime lastModifiedDate;

    private boolean enabled;

    private boolean accountLocked;

    //    private LocalDateTime ;

    private ArrayList<Role> roles;

    public void setFullName(){
        this.fullName =this.lastname+" "+this.firstname;
    }

    public void setLastname(String lastname){
        this.lastname =lastname;
        setFullName();
    }

    public void setFirstname(String firstname){
        this.firstname =firstname;
        setFullName();
    }


}
