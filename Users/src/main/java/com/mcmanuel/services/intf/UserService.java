package com.mcmanuel.services.intf;

import com.mcmanuel.LibraryManagementSystem.DTO.UserDTO;
import com.mcmanuel.LibraryManagementSystem.entities.User;
import com.mcmanuel.LibraryManagementSystem.pojo.UserRequest;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserDTO createUser(UserRequest user);

    UserDTO getUser(UUID userId);

    List<UserDTO> getAllUser();

    UserDTO updateUser(UUID userId, User user);

    UserDTO deleteeUser(UUID userId);

}
