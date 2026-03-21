package com.mcmanuel.LibraryManagementSystem.services.intf;

import com.mcmanuel.LibraryManagementSystem.DTO.UserDTO;
import com.mcmanuel.LibraryManagementSystem.entities.User;
import com.mcmanuel.LibraryManagementSystem.pojo.UserRequest;
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserDTO createUser(UserRequest user);

    UserDTO getUser(UUID userId);

    Pageable getAllUser(int pageNo, int size, String sort);

    UserDTO updateUser(UUID userId, User user);

    String deleteUser(UUID userId);

}
