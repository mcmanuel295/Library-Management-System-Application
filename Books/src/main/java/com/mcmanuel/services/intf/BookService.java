package com.mcmanuel.services.intf;

import com.mcmanuel.LibraryManagementSystem.pojo.BookRequest;
import com.mcmanuel.entities.Books;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface BookService {

    Books createBook(BookRequest bookRequest);

    Books getBook(UUID bookId);

    Page<Books> getAllBook(int pageNo, int size);

    Books updateBook(UUID bookId, Books book);

    String deleteBook(UUID bookId);


}
