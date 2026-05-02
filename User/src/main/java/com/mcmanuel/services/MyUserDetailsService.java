package com.mcmanuel.services;

import com.mcmanuel.entities.User;
import com.mcmanuel.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepo;

    @Override
    @NonNull
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        System.out.println("in userdatail service: "+username);

        User user = userRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " doesn't exist"));
        System.out.println("username;"+username);
        return  new MyUserDetails(user);
    }
}
