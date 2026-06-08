package com.mcmanuel.domain.book;

import com.mcmanuel.exception.BookNotFoundException;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface BookService {

    BookDto addBook(String title);

    BookDto getBook(UUID bookId) throws BookNotFoundException, InterruptedException;

    BookDto getBookByCode(String code) throws BookNotFoundException;

    Page<BookDto> getAllBook(int pageNo, int size, String sort);

    BookDto updateBook(UUID bookId, BookDto book) throws BookNotFoundException;

    String deleteBook(UUID bookId) throws BookNotFoundException;

    BookDto search(String word);

    BookDto borrowBook(UUID userId, UUID bookId) throws InterruptedException;

    BookDto returnBook(UUID userId, UUID bookId) throws InterruptedException;

    List<String> getAllCode();
}
