package com.mcmanuel.web;

import com.mcmanuel.domain.BookDto;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class BookController {

    BookDto addBook(String title);

    BookDto getBook(UUID bookId);

    Page<BookDto> getAllBook(int pageNo, int size);

    BookDto updateBook(UUID bookId, BookDto book);

    String deleteBook(UUID bookId);

    BookDto search(String word);
}
