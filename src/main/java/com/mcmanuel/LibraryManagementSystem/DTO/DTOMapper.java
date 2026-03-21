package com.mcmanuel.LibraryManagementSystem.DTO;

import com.mcmanuel.LibraryManagementSystem.entities.User;

public class DTOMapper {

    public static UserDTO ToDTO(User user){
        return UserDTO.builder()
                .lastname(user.getLastname())
                .firstname(user.getFirstname())
                .email(user.getEmail())
                .roles(user.getRoles())
                .createdDate(user.getCreatedDate())
                .build();
    }

    public static User ToUser(UserDTO dto){

       return User.builder()
               .firstname(dto.getFirstname())
               .lastname(dto.getLastname())
               .email(dto.getEmail())
               .roles(dto.getRoles())
               .createdDate(dto.getCreatedDate())
               .build();
    }
}
