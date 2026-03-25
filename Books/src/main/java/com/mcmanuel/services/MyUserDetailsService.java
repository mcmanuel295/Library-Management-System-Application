package com.mcmanuel.services;

import com.mcmanuel.entities.Books;
import com.mcmanuel.entities.User;
import com.mcmanuel.repository.BookRepository;
import com.mcmanuel.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyBookDetailsService implements UserDetailsService {
    private final BookRepository bookRepo;

    @Override
    @NonNull
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {

        Books book = bookRepo.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User " + username + " doesn't exist"));
        System.out.println("username;"+username);
        return  new MyUserDetails(book);
    }
}
