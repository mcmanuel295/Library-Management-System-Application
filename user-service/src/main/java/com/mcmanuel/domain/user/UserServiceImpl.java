package com.mcmanuel.domain.user;

import com.mcmanuel.book.BookClient;
import com.mcmanuel.book.BookDto;
import com.mcmanuel.config.ApplicationConfiguration;
import com.mcmanuel.domain.email.EmailService;
import com.mcmanuel.domain.token.TokenDto;
import com.mcmanuel.domain.token.TokenService;
import com.mcmanuel.domain.user.request.UserRequest;
import com.mcmanuel.exception.AccountLockedException;
import com.mcmanuel.exception.BookNotFoundException;
import com.mcmanuel.exception.BookNotShareableOrAvailableException;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
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
    private final TokenService tokenRepo;
    private final BookClient client;
    private final ApplicationConfiguration config;

    @Override
    public UserDTO createUser(UserRequest request) throws MessagingException {
        ArrayList<Role> roles = new ArrayList<>();
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
        return UserMapper.ToDTO(userRepo.save(user));
    }

    private void sendValidationEmail(User user) throws MessagingException {
        TokenDto token = generateToken();
        emailService.sendEmail(
                user.getEmail(),
                user.getFullName(),
                "ACTIVATE-EMAIL",
                token.token(),
                "Account-activation",
                "Account_Activation");
    }

    private boolean accountLocked(UUID userId){
        User user =userRepo.findById(userId).orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
        return user.isAccountLocked() && user.isEnabled();
    }

    private TokenDto generateToken() {
        var token = TokenDto.builder()
                .token(generateToken.apply(6))
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .build();
        return tokenRepo.saveToken(token);
    }


    private final Function<Integer, String> generateToken = (length) -> {
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
        if (accountLocked(userId))
            throw new AccountLockedException("Account Is Not Unlocked");
        return UserMapper.ToDTO(userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User with userId" + userId + " not found")));
    }

    @Override
    public Page<UserDTO> getAllUser(int pageNo, int size) {
        Pageable pageable = PageRequest.of(pageNo, size);
        return userRepo.findAll(pageable).map(UserMapper::ToDTO);
    }

    @Override
    public UserDTO updateUser(UUID userId, UserDTO updatedUser) {
        if (accountLocked(userId))
            throw new AccountLockedException("Account Is Not Unlocked");

       UserMapper.ToDTO(userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User with userId" + userId + " not found")));

        updatedUser = new UserDTO(
          userId,
          updatedUser.fullName(),
          updatedUser.email(),
          updatedUser.roles(),
          updatedUser.createdDate()
        );
        return UserMapper.ToDTO(userRepo.save(UserMapper.ToUser(updatedUser)));
    }

    @Override
    public String deleteUser(UUID userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User with userId" + userId + " not found"));
        userRepo.deleteById(user.getUserId());
        return "deleted";
    }

    @Override
    public String login(String email, String password) {
        System.out.println("enter");
        Authentication auth = manager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        System.out.println(auth.isAuthenticated());
        if (auth.isAuthenticated()) {
            UserDTO user = UserMapper.ToDTO(userRepo.findByEmail(email).orElseThrow());
            log.info("Authenticated");
            return jwtService.generateToken(user);
        }
        throw new UsernameNotFoundException("invalid user login");
    }

    @Override
    public UserDTO updateRole(UUID userId, Role role) {
        if (accountLocked(userId))
            throw new AccountLockedException("Account Is Not Unlocked");
        User user = userRepo.findById(userId).orElseThrow();

        user.getRoles().add(role);
        return UserMapper.ToDTO(userRepo.save(user));
    }

    @Override
    public boolean activateAccount(String email, String token) {
        Optional<User> userOpt = userRepo.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException("User not found!!");
        }
        User user = userOpt.get();
        user.setAccountLocked(false);
        user.setEnabled(true);
        return false;
    }

    @Override
    public String borrowBook(UUID userId, UUID bookId) {
        try {
            return client.borrowBook(userId, bookId);
        } catch (BookNotFoundException ex) {
            throw new BookNotFoundException("Book Not Found");
        } catch (BookNotShareableOrAvailableException ex) {
            throw new BookNotShareableOrAvailableException("Book Not Shareable Or Available");
        }
    }

    @Override
    public String returnBook(UUID userId, UUID bookId) {
        try {
            return client.returnBook(userId, bookId);
        } catch (BookNotFoundException ex) {
            throw new BookNotFoundException("Book Not Found");
        }
    }

    @Override
    public Page<BookDto> getAllBook() {
        int pageNo = config.pageNo();
        int size = config.size();
        String sort = config.sort();

        System.out.println("page No" + pageNo + " size " + size + " sort " + sort);

        return client.getAllBook(pageNo, size, sort);
    }
}
