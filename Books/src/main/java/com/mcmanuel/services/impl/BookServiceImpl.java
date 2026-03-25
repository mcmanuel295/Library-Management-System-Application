package com.mcmanuel.services.impl;

import com.mcmanuel.LibraryManagementSystem.pojo.BookRequest;
import com.mcmanuel.entities.Books;
import com.mcmanuel.repository.BookRepository;
import com.mcmanuel.services.JwtService;
import com.mcmanuel.services.intf.BookService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager manager;
    private final JwtService jwtService;

    @Override
    public Books createBook(BookRequest request) {
        Books book = Books.builder()
                .title(request.getTitle())
                .build();

        book.setCreatedDate(LocalDateTime.now());

        return bookRepo.save(book);
    }

    @Override
    public Books getBook(UUID bookId) {
        return bookRepo.findById(bookId).orElseThrow(()->new RuntimeException("Book with bookId"+bookId+" not found"));
    }

    @Override
    public Page<Books> getAllBook(int pageNo, int size) {
        Pageable pageable = PageRequest.of(pageNo,size);
        return bookRepo.findAll(pageable);

    }

    @Override
    public Books updateBook(UUID bookId, Books updatedBook) {
        Books book =bookRepo.findById(bookId).orElseThrow(()->new RuntimeException("Book with bookId"+bookId+" not found"));
        updatedBook.setBookId(book.getBookId());
        return bookRepo.save(updatedBook);
    }

    @Override
    public String deleteBook(UUID bookId) {
        Books book =bookRepo.findById(bookId).orElseThrow(()->new RuntimeException("Book with bookId"+bookId+" not found"));
        bookRepo.deleteById(book.getBookId());
        return "deleted";
    }


}
