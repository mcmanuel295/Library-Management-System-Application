package com.mcmanuel.domain.user;

import com.mcmanuel.book.BookDto;
import com.mcmanuel.domain.user.request.UserRequest;
import jakarta.mail.MessagingException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Page;

public interface UserService {

    UserDTO createUser(UserRequest user) throws MessagingException;

    UserDTO getUser(UUID userId);

    Page<UserDTO> getAllUser(int pageNo, int size);

    UserDTO updateUser(UUID userId, UserDTO user);

    String deleteUser(UUID userId);

    String login(String email, String password);

    UserDTO updateRole(UUID userId, Role role);

    boolean activateAccount(String email, String token);

    String borrowBook(UUID userId, UUID bookId);

    String returnBook(UUID userId, UUID bookId);

    Page<BookDto> getAllBook();

    CompletableFuture<BookDto> getBook(UUID bookId);
}
