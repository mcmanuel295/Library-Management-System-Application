package com.mcmanuel.web;

import com.mcmanuel.domain.Book;
import com.mcmanuel.domain.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookOperationController {
    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity<String> borrowBook(@PathVariable UUID userId,@PathVariable @Valid UUID bookId) {
        Book borrowedBook = userService.borrowBook(userId,bookId);
        if (borrowedBook != null) {
            return new ResponseEntity<>("Book " + bookId + " borrowed", HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<>("Book " + bookId + " not found", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/books/")
    public ResponseEntity<ArrayList<Book>> getAllBook() {
        return new ResponseEntity<>(userService.getAllBook(), HttpStatus.BAD_REQUEST);

    }

    @PutMapping()
    public String returnBook() {
        return null;
    }
}





