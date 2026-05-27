package com.mcmanuel.web;

import com.mcmanuel.domain.Book;
import com.mcmanuel.domain.BorrowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/borrow")
public class BorrowController {
    private final BorrowService borrowService;

    @PostMapping("/")
    public ResponseEntity<String> borrowBook(@PathVariable UUID userId, @PathVariable @Valid UUID bookId) {
        Book borrowedBook = borrowService.borrowBook(userId,bookId);
        if (borrowedBook != null) {
            return new ResponseEntity<>("Book " + bookId + " borrowed", HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<>("Book " + bookId + " not found", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/books/")
    public ResponseEntity<ArrayList<Book>> getAllBook() {
        return new ResponseEntity<>(borrowService.getAllBook(), HttpStatus.BAD_REQUEST);

    }

    @PutMapping()
    public String returnBook() {
        return null;
    }
}
