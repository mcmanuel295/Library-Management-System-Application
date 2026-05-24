package com.mcmanuel.web;

import com.mcmanuel.domain.Book;
import com.mcmanuel.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookOperationController {
    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<String> borrowBook(@PathVariable UUID bookId){
        Book borrowedBook = userService.borrowBook(bookId);
    if ( borrowedBook != null) {
        return new ResponseEntity<>("Book "+bookId+" borrowed", HttpStatus.BAD_REQUEST);

        }
    return new ResponseEntity<>("Book "+bookId+" not found", HttpStatus.BAD_REQUEST);
    }

    @GetMapping()
    public String getAllBook(){}

    @GetMapping()
    public String returnBook(){}

    @GetMapping()
    public String borrowBook(){}

    @GetMapping()
    public String borrowBook(){}

    @GetMapping()
    public String borrowBook(){}

    @GetMapping()
    public String borrowBook(){}
}
