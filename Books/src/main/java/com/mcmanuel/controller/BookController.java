package com.mcmanuel.controller;

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
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookService bookService;

    @PostMapping("/")
    public ResponseEntity<Books> createBook(@RequestBody BookRequest request){
        return new ResponseEntity<>(bookService.createBook(request),HttpStatus.CREATED);
    }

    @GetMapping("/{bookId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Books> getBook(@PathVariable UUID bookId){
        Books book =bookService.getBook(bookId);
        if (book == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(book,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/")
    ResponseEntity<Page<Books>> getAllBooks(
            @RequestParam(required = false,defaultValue = "0") int pageNo, @RequestParam(required = false,defaultValue = "10") int size) {
        return ResponseEntity.ok(bookService.getAllBook(pageNo,size));
    }

    @PutMapping("/{bookId}")
    ResponseEntity<Books> updateBook(@PathVariable UUID bookId, @RequestBody Books updatedBook){
        Books savedBook = bookService.updateBook(bookId,updatedBook);
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
