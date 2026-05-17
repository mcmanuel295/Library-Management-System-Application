package com.mcmanuel.domain;

import org.springframework.data.domain.Page;

import java.util.UUID;

public interface BookService {

    BookDto addBook(String title);

    BookDto getBook(UUID bookId);

    Page<BookDto> getAllBook(int pageNo, int size);

    BookDto updateBook(UUID bookId, BookDto book);

    String deleteBook(UUID bookId);

    BookDto search(String word);
}
