package com.mcmanuel.controller;

import com.mcmanuel.domain.BookDto;
import com.mcmanuel.domain.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookService bookService;

    @PostMapping("/")
    public ResponseEntity<BookDto> createBook(@RequestBody String title){
        return new ResponseEntity<>(bookService.addBook(title),HttpStatus.CREATED);
    }

    @GetMapping("/{bookId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<BookDto> getBook(@PathVariable UUID bookId){
        BookDto book =bookService.getBook(bookId);
        if (book == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(book,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/")
    ResponseEntity<Page<BookDto>> getAllBooks(
            @RequestParam(required = false,defaultValue = "0") int pageNo, @RequestParam(required = false,defaultValue = "10") int size) {
        return ResponseEntity.ok(bookService.getAllBook(pageNo,size));
    }

    @PutMapping("/{bookId}")
    ResponseEntity<BookDto> updateBook(@PathVariable UUID bookId, @RequestBody BookDto updatedBook){
        BookDto savedBook = bookService.updateBook(bookId,updatedBook);
        if (savedBook == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(savedBook,HttpStatus.OK);
    }

    @DeleteMapping("/{bookId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    ResponseEntity<String> deleteBook(@PathVariable UUID bookId){
        String savedBook = bookService.deleteBook(bookId);
        if (savedBook == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(savedBook,HttpStatus.OK);
    }





}
