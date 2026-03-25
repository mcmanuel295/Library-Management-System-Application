package com.mcmanuel.controller;

import com.mcmanuel.DTO.UserDTO;
import com.mcmanuel.LibraryManagementSystem.pojo.BookRequest;
import com.mcmanuel.entities.Books;
import com.mcmanuel.services.intf.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class BookController {
    private final BookService bookService;

    @PostMapping("/")
    public ResponseEntity<UserDTO> createUser(@RequestBody BookRequest request){
        return new ResponseEntity<>(bookService.createUser(request),HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<UserDTO> getUser(@PathVariable UUID userId){
        UserDTO dto =bookService.getBook(userId);
        if (dto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/")
    ResponseEntity<Page<UserDTO>> getAllUser(
            @RequestParam(required = false,defaultValue = "0") int pageNo, @RequestParam(required = false,defaultValue = "10") int size) {
        return ResponseEntity.ok(bookService.getAllUser(pageNo,size));
    }

    @PutMapping("/{userId}")
    ResponseEntity<UserDTO> updateUser(@PathVariable UUID userId, @RequestBody Books updatedBook){
        UserDTO savedUser = bookService.updateUser(userId,updatedBook);
        if (savedUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(savedUser,HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    ResponseEntity<String> deleteUser(@PathVariable UUID userId){
        String savedUser = bookService.deleteUser(userId);
        if (savedUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(savedUser,HttpStatus.OK);
    }





}
