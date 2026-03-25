package com.mcmanuel.services.intf;

import com.mcmanuel.DTO.UserDTO;
import com.mcmanuel.entities.User;
import com.mcmanuel.pojo.UserRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.UUID;

public interface UserService {

    UserDTO createUser(UserRequest user);

    UserDTO getUser(UUID userId);

    Page<UserDTO> getAllUser(int pageNo, int size);

    UserDTO updateUser(UUID userId, User user);

    String deleteUser(UUID userId);

    String login(String email, String password);
}
