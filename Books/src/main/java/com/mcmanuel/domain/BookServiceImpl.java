package com.mcmanuel.domain;

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
    public BookDto addBook(String title) {
        Optional<Book> optionalObj =bookRepo.findBytitle(title);
        if(optionalObj.isEmpty()){
            Book book = new Book();
            book.setAvailable(true);
            book.setQuantity(1);
            book.setCreatedDate(LocalDateTime.now());

            return DtoMapper.Dto(bookRepo.save(book));
        }
        Book book =optionalObj.get();
        book.setQuantity(book.getQuantity()+1);
        return DtoMapper.Dto(bookRepo.save(book));
    }


    @Override
    public BookDto getBook(UUID bookId) {
        return DtoMapper.Dto(
                bookRepo.findById(bookId).orElseThrow(
                ()->new RuntimeException("Book with bookId"+bookId+" not found")
                )
        );
    }

    @Override
    public Page<BookDto> getAllBook(int pageNo, int size) {
        Pageable pageable = PageRequest.of(pageNo,size);
        return bookRepo.findAll(pageable).map(
                DtoMapper::Dto
        );

    }

    @Override
    public BookDto updateBook(UUID bookId, BookDto updatedBook) {
        Book book =bookRepo.findById(bookId).orElseThrow(()->new RuntimeException("Book with bookId"+bookId+" not found"));
        updatedBook.bookId(book.getBookId());
        return DtoMapper.Dto(bookRepo.save(updatedBook);
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
