package com.mcmanuel.domain.user;

import com.mcmanuel.entities.User;

public class DTOMapper {

    public static UserDTO ToDTO(User user){
        return UserDTO.builder()
                .userId(user.getUserId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .roles(user.getRoles())
                .createdDate(user.getCreatedDate())
                .build();
    }

    public static User ToUser(UserDTO dto){

       return User.builder()
               .userId(dto.getUserId())
               .fullName(dto.getFullName())
               .email(dto.getEmail())
               .roles(dto.getRoles())
               .createdDate(dto.getCreatedDate())
               .build();
    }
}
