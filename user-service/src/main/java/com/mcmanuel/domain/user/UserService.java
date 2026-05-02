package com.mcmanuel.domain.user;

import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface UserService {

    UserDTO createUser(UserRequest user) throws MessagingException;

    UserDTO getUser(UUID userId);

    Page<UserDTO> getAllUser(int pageNo, int size);

    UserDTO updateUser(UUID userId, User user);

    String deleteUser(UUID userId);

    String login(String email, String password);

    UserDTO updateRole(UUID userId, Role role);

    boolean activateAccount(String email, String token);
}
