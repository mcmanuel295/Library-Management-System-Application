package com.mcmanuel.domain.services;

import com.mcmanuel.domain.book.Books;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface BookService {

    Books addBook(String title);

    Books getBook(UUID bookId);

    Page<Books> getAllBook(int pageNo, int size);

    Books updateBook(UUID bookId, Books book);

    String deleteBook(UUID bookId);

    Books search(String word);
}
