package com.mcmanuel.domain.user;

import com.mcmanuel.book.Book;
import com.mcmanuel.domain.user.request.UserRequest;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.UUID;

public interface UserService {

    UserDTO createUser(UserRequest user) throws MessagingException;

    UserDTO getUser(UUID userId);

    Page<UserDTO> getAllUser(int pageNo, int size);

    UserDTO updateUser(UUID userId, UserDTO user);

    String deleteUser(UUID userId);

    String login(String email, String password);

    UserDTO updateRole(UUID userId, Role role);

    boolean activateAccount(String email, String token);

    Book borrowBook(UUID userIs,UUID bookId);

    ArrayList<Book> getAllBook();
}
