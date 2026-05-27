package com.mcmanuel.domain.book;

import com.mcmanuel.exception.BookNotFoundException;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface BookService {

    BookDto addBook(String title);

    BookDto getBook(UUID bookId) throws BookNotFoundException;

    Page<BookDto> getAllBook(int pageNo, int size,String sort);

    BookDto updateBook(UUID bookId, BookDto book)throws BookNotFoundException;

    String deleteBook(UUID bookId) throws BookNotFoundException;

    BookDto search(String word);
}
