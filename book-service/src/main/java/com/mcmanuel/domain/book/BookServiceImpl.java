package com.mcmanuel.domain.book;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookServiceImpl implements BookService {
    @Override
    public BookDto addBook(String title) {
        return null;
    }

    @Override
    public BookDto getBook(UUID bookId) {
        return null;
    }

    @Override
    public Page<BookDto> getAllBook(int pageNo, int size) {
        return null;
    }

    @Override
    public BookDto updateBook(UUID bookId, BookDto updatedBook) {
        return null;
    }

    @Override
    public String deleteBook(UUID bookId) {
        return "";
    }
}