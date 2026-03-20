package com.mcmanuel.LibraryManagementSystem.entities;

import com.mcmanuel.LibraryManagementSystem.pojo.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;
    @NonNull
    @NotBlank(message = "this field cannot be blank")
    private String lastname;

    @Column(nullable = false)
    @NotBlank(message = "this field cannot be blank")
    private String firstname;

    @Column(nullable = false)
    @NotBlank(message = "this field cannot be blank")
    private String fullName =getFullName();

    @Column(unique = true,nullable = false)
    @Email
    private String email;

    @NotBlank(message = "this field cannot be blank")
    private String password;

    @Column(nullable = false,updatable = false,insertable = false)
    private LocalDateTime createdDate;
//    private LocalDateTime ;

    private List<Role> role;
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
