package com.mcmanuel.services;

import com.mcmanuel.entities.Books;
import com.mcmanuel.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepo;
    private final AuthenticationManager manager;

    @Override
    public Books addBook(String title) {
        Optional<Books> optionalObj =bookRepo.findBytitle(title);
        if(optionalObj.isEmpty()){
            Books book = new Books(title);
            book.setAvailable(true);
            book.setQuantity(1);
            book.setCreatedDate(LocalDateTime.now());

            return bookRepo.save(book);
        }
        Books book = new Books(title);
        book.setQuantity(book.getQuantity()+1);

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

    @Override
    public Books search(String word) {
        return bookRepo.search(word);
    }


}
