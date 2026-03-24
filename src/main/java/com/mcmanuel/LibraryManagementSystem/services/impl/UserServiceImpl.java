package com.mcmanuel.LibraryManagementSystem.services.impl;

import com.mcmanuel.LibraryManagementSystem.DTO.DTOMapper;
import com.mcmanuel.LibraryManagementSystem.DTO.UserDTO;
import com.mcmanuel.LibraryManagementSystem.entities.User;
import com.mcmanuel.LibraryManagementSystem.pojo.Role;
import com.mcmanuel.LibraryManagementSystem.pojo.UserRequest;
import com.mcmanuel.LibraryManagementSystem.repository.UserRepository;
import com.mcmanuel.LibraryManagementSystem.services.JwtService;
import com.mcmanuel.LibraryManagementSystem.services.intf.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager manager;
    private final JwtService jwtService;

    @Override
    public UserDTO createUser(UserRequest request) {
        ArrayList<Role> roles =new ArrayList<>();
        roles.add(Role.USER);
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .build();

        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setCreatedDate(LocalDateTime.now());

        return DTOMapper.ToDTO(userRepo.save(user));
    }

    @Override
    public UserDTO getUser(UUID userId) {
        return DTOMapper.ToDTO(userRepo.findById(userId).orElseThrow(()->new RuntimeException("User with userId"+userId+" not found")));
    }

    @Override
    public Pageable getAllUser(int pageNo, int size, String sortBy) {
        return sortBy.equals("asc")? PageRequest.of(pageNo,size,Sort.by(Sort.Direction.ASC,"userId"))
                :PageRequest.of(pageNo,size, Sort.Direction.DESC,"userId");
    }

    @Override
    public UserDTO updateUser(UUID userId, User updatedUser) {
        User user =userRepo.findById(userId).orElseThrow(()->new RuntimeException("User with userId"+userId+" not found"));
        updatedUser.setUserId(user.getUserId());
        return DTOMapper.ToDTO(userRepo.save(updatedUser));
    }

    @Override
    public String deleteUser(UUID userId) {
        User user =userRepo.findById(userId).orElseThrow(()->new RuntimeException("User with userId"+userId+" not found"));
        userRepo.deleteById(user.getUserId());
        return "deleted";
    }

    @Override
    public String login(String email, String password) {
        Authentication auth = manager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
       if(auth.isAuthenticated()){
           User user= userRepo.findByEmail(email).orElseThrow();
           log.info( "Authenticated");
           return jwtService.generateToken(user);
       }
        throw new UsernameNotFoundException("invalid user login");
    }
}
