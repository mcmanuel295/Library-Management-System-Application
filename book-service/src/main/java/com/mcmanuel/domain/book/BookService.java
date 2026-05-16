package com.mcmanuel.domain.book;

import org.springframework.data.domain.Page;

import java.util.UUID;

public interface BookService {
    BookDto addBook(String title);
    BookDto getBook(UUID bookId);

    Page<BookDto> getAllBook(int pageNo, int size);

    BookDto updateBook(UUID bookId, BookDto updatedBook);

    String deleteBook(UUID bookId);
}