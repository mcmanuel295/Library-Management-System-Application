package com.mcmanuel.domain.user;

import java.io.Serializable;

class UserMapper implements Serializable {

    public static UserDTO ToDTO(User user) {
        return UserDTO.builder()
                .userId(user.getUserId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .roles(user.getRoles())
                .createdDate(user.getCreatedDate())
                .build();
    }

    public static User ToUser(UserDTO dto) {

        return User.builder()
                .userId(dto.userId())
                .fullName(dto.fullName())
                .email(dto.email())
                .roles(dto.roles())
                .createdDate(dto.createdDate())
                .build();
    }
}
