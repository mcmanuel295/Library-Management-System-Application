package com.mcmanuel.LibraryManagementSystem.services;

import com.mcmanuel.LibraryManagementSystem.entities.User;
import com.mcmanuel.LibraryManagementSystem.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User " + username + " doesn't exist"));
        return new MyUserDetails(user);
    }
}
