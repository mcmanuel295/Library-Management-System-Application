package com.mcmanuel.services.impl;

import com.mcmanuel.domain.user.DTOMapper;
import com.mcmanuel.domain.user.UserDTO;
import com.mcmanuel.entities.User;
import com.mcmanuel.domain.user.Role;
import com.mcmanuel.pojo.Token;
import com.mcmanuel.domain.user.UserRequest;
import com.mcmanuel.repository.TokenRepository;
import com.mcmanuel.repository.UserRepository;
import com.mcmanuel.services.JwtService;
import com.mcmanuel.services.intf.EmailService;
import com.mcmanuel.services.intf.UserService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager manager;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final TokenRepository tokenRepo;


    @Override
    public UserDTO createUser(UserRequest request) throws MessagingException {
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
        user.setEnabled(false);
        user.setAccountLocked(false);

        sendValidationEmail(user);
        return DTOMapper.ToDTO(userRepo.save(user));
    }

    private void sendValidationEmail(User user) throws MessagingException {
        Token token = generateToken();
        emailService.sendEmail(user.getEmail(),user.getFullName(),"ACTIVATE-EMAIL",token.getToken(),"Account-activation","Account_Activation");
    }

    private Token generateToken(){
        var token = Token
                .builder()
                .token(generateToken.apply(6))
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .build();
        return tokenRepo.save(token);
    }


    private final Function<Integer,String> generateToken = (length)->{
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder builder = new StringBuilder();

        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            builder.append(chars.charAt(random.nextInt(chars.length())));
        }
        return builder.toString();
    };

    @Override
    public UserDTO getUser(UUID userId) {
        return DTOMapper.ToDTO(userRepo.findById(userId).orElseThrow(()->new RuntimeException("User with userId"+userId+" not found")));
    }

    @Override
    public Page<UserDTO> getAllUser(int pageNo, int size) {
        Pageable pageable = PageRequest.of(pageNo,size);
        return userRepo.findAll(pageable).map(DTOMapper::ToDTO);

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
        System.out.println("enter");
        Authentication auth = manager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
        System.out.println(auth.isAuthenticated());
       if(auth.isAuthenticated()){
           User user= userRepo.findByEmail(email).orElseThrow();
           log.info( "Authenticated");
       return jwtService.generateToken(user);
       }
        throw new UsernameNotFoundException("invalid user login");
    }

    @Override
    public UserDTO updateRole(UUID userId,Role role) {
        User user =userRepo.findById(userId).orElseThrow();

        user.getRoles().add(role);
        return DTOMapper.ToDTO(userRepo.save(user));
    }

    @Override
    public boolean activateAccount(String email, String token) {
        Optional<User> userOpt =userRepo.findByEmail(email);
        if(userOpt.isEmpty()){
            throw new UsernameNotFoundException("User not found!!");
        }
        User user = userOpt.get();
        user.setAccountLocked(false);
        user.setEnabled(true);
        return false;
    }
}
