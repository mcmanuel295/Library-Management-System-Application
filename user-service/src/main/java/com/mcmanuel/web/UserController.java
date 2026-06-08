package com.mcmanuel.web;

import com.mcmanuel.book.BookDto;
import com.mcmanuel.domain.user.Role;
import com.mcmanuel.domain.user.UserDTO;
import com.mcmanuel.domain.user.UserService;
import com.mcmanuel.domain.user.request.LoginRequest;
import com.mcmanuel.domain.user.request.UserRequest;
import com.mcmanuel.exception.BookNotFoundException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import jakarta.mail.MessagingException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserRequest request) throws MessagingException {
        return new ResponseEntity<>(userService.createUser(request), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<UserDTO> getUser(@PathVariable UUID userId) throws MessagingException {
        UserDTO dto = userService.getUser(userId);
        if (dto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/")
    ResponseEntity<Page<UserDTO>> getAllUser(
            @RequestParam(required = false, defaultValue = "0") int pageNo,
            @RequestParam(required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok(userService.getAllUser(pageNo, size));
    }

    @PutMapping("/{userId}")
    ResponseEntity<UserDTO> updateUser(@PathVariable UUID userId, @RequestBody UserDTO updatedUser) {
        UserDTO savedUser = userService.updateUser(userId, updatedUser);
        if (savedUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    ResponseEntity<String> deleteUser(@PathVariable UUID userId) {
        String savedUser = userService.deleteUser(userId);
        if (savedUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String login = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (login == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(login, HttpStatus.OK);
    }

    @PutMapping("/{userId}/role")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    ResponseEntity<UserDTO> updateUserRole(@PathVariable UUID userId, @RequestBody Role role) {
        UserDTO savedUser = userService.updateRole(userId, role);
        if (savedUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    @PostMapping("/{email}/{token}")
    ResponseEntity<String> activateUser(@PathVariable String email, @PathVariable String token) {
        if (!userService.activateAccount(email, token)) {
            return new ResponseEntity<>("Error", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok("Account activated");
    }


//    BOOK SERVICE OPERATIONS

//    @CircuitBreaker(name="catalog",fallbackMethod = "circuitFallBackMethod")
    @TimeLimiter(name = "catalog",fallbackMethod = "timelimiterFallBackMethod")
    @Retry(name = "catalog")
    @GetMapping("/book/{bookId}")
    public CompletableFuture<BookDto> getBook(@PathVariable UUID bookId) throws InterruptedException {
        return CompletableFuture.supplyAsync(()->{
            BookDto bookDto=null;
            try {
                bookDto =userService.getBook(bookId);
                if (bookDto == null) {
                    throw new BookNotFoundException("Book Not Found");
                }
            }
            catch(InterruptedException ex){System.out.println(ex.getMessage());}
            catch(BookNotFoundException ex){throw new BookNotFoundException(ex.getMessage());}

            return bookDto;
        });
    }


    private CompletableFuture<BookDto> circuitFallBackMethod(UUID bookId, RuntimeException exception){
        return CompletableFuture.supplyAsync(()->new BookDto(null,"Oops! error",false,false,0,null,null,null));
    }
    private CompletableFuture<BookDto> timelimiterFallBackMethod(UUID bookId, RuntimeException exception){
        return CompletableFuture.supplyAsync(()-> new BookDto(null,"Oops! something went wrong, please order after sometime",false,false,0,null,null,null));
    }




    @GetMapping("/all-books")
    public ResponseEntity<Page<BookDto>> getAllBook() {
        Page<BookDto> books = userService.getAllBook();

        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping("/{userId}/borrow")
    public ResponseEntity<String> borrowBook(@PathVariable UUID userId, @RequestParam UUID bookId) {
        String borrowBook = userService.borrowBook(userId, bookId);
        if (borrowBook == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(borrowBook, HttpStatus.OK);
    }

    @PostMapping("/{userId}/return")
    public ResponseEntity<String> returnBook(@RequestParam UUID userId, @RequestParam UUID bookId) {
        String borrowBook = userService.returnBook(userId, bookId);
        if (borrowBook == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(borrowBook, HttpStatus.OK);
    }
}
