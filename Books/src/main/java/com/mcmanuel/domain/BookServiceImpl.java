package com.mcmanuel.domain;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Override
    public BookDto addBook(String title) {
        Optional<Book> optionalObj =bookRepo.findByTitle(title);
        if(optionalObj.isEmpty()){
            Book book = new Book();
            book.setAvailable(true);
            book.setQuantity(1);
            book.setCreatedDate(LocalDateTime.now());

            return DtoMapper.toDto(bookRepo.save(book));
        }
        Book book =optionalObj.get();
        book.setQuantity(book.getQuantity()+1);
        return DtoMapper.toDto(bookRepo.save(book));
    }


    @Override
    public BookDto getBook(UUID bookId) {
        return DtoMapper.toDto(
                bookRepo.findById(bookId).orElseThrow(
                ()->new RuntimeException("Book with bookId"+bookId+" not found")
                )
        );
    }

    @Override
    public Page<BookDto> getAllBook(int pageNo, int size) {
        Pageable pageable = PageRequest.of(pageNo,size);
        return bookRepo.findAll(pageable).map(
                DtoMapper::toDto
        );

    }

    @Override
    public BookDto updateBook(UUID bookId, BookDto updatedBook) {
        Book book =bookRepo.findById(bookId).orElseThrow(()->new RuntimeException("Book with bookId"+bookId+" not found"));
        updatedBook = updatedBook.withBookId(book.getBookId());
        return DtoMapper.toDto(
                bookRepo.save(DtoMapper.toBook(updatedBook))
        );
    }

    @Override
    public String deleteBook(UUID bookId) {
        Book book =bookRepo.findById(bookId).orElseThrow(()->new RuntimeException("Book with bookId"+bookId+" not found"));
        bookRepo.deleteById(book.getBookId());
        return "deleted";
    }

    @Override
    public BookDto search(String word) {
        return DtoMapper.toDto(bookRepo.search(word));
    }


}
