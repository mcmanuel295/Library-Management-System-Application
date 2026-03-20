package com.mcmanuel.LibraryManagementSystem.services.impl;

import com.mcmanuel.LibraryManagementSystem.DTO.UserDTO;
import com.mcmanuel.LibraryManagementSystem.entities.User;
import com.mcmanuel.LibraryManagementSystem.pojo.UserRequest;
import com.mcmanuel.LibraryManagementSystem.services.intf.UserService;

import java.util.List;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    @Override
    public UserDTO createUser(UserRequest user) {
        return null;
    }

    @Override
    public UserDTO getUser(UUID userId) {
        return null;
    }

    @Override
    public List<UserDTO> getAllUser() {
        return List.of();
    }

    @Override
    public UserDTO updateUser(UUID userId, User user) {
        return null;
    }

    @Override
    public UserDTO deleteeUser(UUID userId) {
        return null;
    }
}
