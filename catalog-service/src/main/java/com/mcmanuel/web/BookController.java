package com.mcmanuel.web;

import com.mcmanuel.configuration.ApplicationConfiguration;
import com.mcmanuel.domain.book.BookDto;
import com.mcmanuel.domain.book.BookService;
import com.mcmanuel.exception.BookNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookService bookService;
    private final ApplicationConfiguration applicationConfig;


    @PostMapping("/")
    ResponseEntity<BookDto> addBook(@RequestParam @Valid  String title){
        return new ResponseEntity<>(bookService.addBook(title),HttpStatus.CREATED);
    }

    @GetMapping("/{bookId}")
    ResponseEntity<BookDto> getBook(@Valid @PathVariable UUID bookId){
        try {
            return new ResponseEntity<>(bookService.getBook(bookId),HttpStatus.OK);
        }
        catch (BookNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/")
    Page<BookDto> getAllBook(@Valid @RequestParam(defaultValue ="0" ) int pageNo,@Valid @RequestParam(defaultValue = "10") int size,@Valid @RequestParam(required = false,defaultValue = "title")  String sort){
        return bookService.getAllBook(pageNo,size,sort);
    }

    @PutMapping("/{bookId}")
    ResponseEntity<BookDto> updateBook(@Valid @PathVariable UUID bookId, @RequestBody @Valid BookDto book){
        try {
            return new ResponseEntity<>(bookService.updateBook(bookId,book),HttpStatus.OK);
        }
        catch (BookNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{bookId}")
    ResponseEntity<String> deleteBook(@Valid @PathVariable UUID bookId) throws BookNotFoundException{
        try {
            return new ResponseEntity<>( bookService.deleteBook(bookId), HttpStatus.OK);
        }
        catch (BookNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/search")
    ResponseEntity<BookDto> search(@RequestParam @Valid String word){
        try {
            return new ResponseEntity<>( bookService.search(word), HttpStatus.OK);
        }
        catch (BookNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/")
    public ResponseEntity<String> borrowBook(@RequestParam UUID userId, @RequestParam @Valid UUID bookId) {
        BookDto borrowedBook = bookService.borrowBook(userId,bookId);
        if (borrowedBook != null) {
            return new ResponseEntity<>("Book " + bookId + " borrowed by user "+userId, HttpStatus.OK);

        }
        return new ResponseEntity<>("Book " + bookId + " not found", HttpStatus.BAD_REQUEST);
    }


    @PutMapping()
    public ResponseEntity<String> returnBook(@RequestParam UUID userId,@RequestParam UUID bookId) {
        BookDto borrowedBook = bookService.returnBook( userId,bookId);

        if (borrowedBook != null) {
            return new ResponseEntity<>("Book " + bookId + " returned", HttpStatus.OK);

        }
        return new ResponseEntity<>("Book " + bookId + " not found", HttpStatus.BAD_REQUEST);

    }
}
