package com.mcmanuel.web;

import com.mcmanuel.domain.BookDto;
import com.mcmanuel.domain.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/")
public class BookController {
    private final BookService bookService;

    @PostMapping("/")
    ResponseEntity<BookDto> addBook(String title){
        return new ResponseEntity<>(bookService.addBook(title), HttpStatus.CREATED);
    }

    ResponseEntity<BookDto> getBook(UUID bookId){
    }

    Page<BookDto> getAllBook(int pageNo, int size, String sort){

    }

    ResponseEntity<BookDto> updateBook(UUID bookId, BookDto book){

    }

    String deleteBook(UUID bookId){

    }

    ResponseEntity<BookDto> search(String word){

    }
}
