package com.mcmanuel.domain.book;

import com.mcmanuel.configuration.ApplicationConfiguration;
import com.mcmanuel.exception.BookNotAvailableException;
import com.mcmanuel.exception.BookNotFoundException;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepo;
    private final RabbitTemplate rabbitTemplate;
    private final ApplicationConfiguration config;

    @Override
    public BookDto addBook(String title) {
        Optional<Book> optionalObj = bookRepo.findByTitle(title.trim());
        if (optionalObj.isEmpty()) {

            Book book = new Book();
            book.setTitle(title);
            book.setAvailable(true);
            book.setShareable(true);
            book.setQuantity(1);
            book.setCreatedDate(LocalDateTime.now());

            return DtoMapper.toDto(bookRepo.save(book));
        }

        Book book = optionalObj.get();
        book.setQuantity(book.getQuantity() + 1);
        return DtoMapper.toDto(book);
    }

    @Override
    public BookDto getBook(UUID bookId) throws BookNotFoundException {
        return DtoMapper.toDto(bookRepo.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book with bookId" + bookId + " not found")));
    }

    @Override
    public Page<BookDto> getAllBook(int pageNo, int size, String sort) {
        Sort sortBy = Sort.by(sort).ascending();
        pageNo = pageNo <= 1 ? 0 : pageNo - 1;

        Pageable pageable = PageRequest.of(pageNo, size, sortBy);
        return bookRepo.findAll(pageable).map(DtoMapper::toDto);
    }

    @Override
    public BookDto updateBook(UUID bookId, BookDto updatedBook) throws BookNotFoundException {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book with bookId" + bookId + " not found"));
        updatedBook = updatedBook.withBookId(book.getBookId());
        return DtoMapper.toDto(bookRepo.save(DtoMapper.toBook(updatedBook)));
    }

    @Override
    public String deleteBook(UUID bookId) throws BookNotFoundException {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book with bookId" + bookId + " not found"));
        bookRepo.deleteById(book.getBookId());
        return "deleted";
    }

    @Override
    public BookDto search(String word) {
        Optional<Book> optBook = bookRepo.findByTitleContaining(word);

        if (optBook.isPresent()) {
            return DtoMapper.toDto(optBook.get());
        }
        throw new BookNotFoundException("Book with word " + word + " not found");
    }

    @Override
    public BookDto borrowBook(UUID userId, UUID bookId) {
        Book book = DtoMapper.toBook(getBook(bookId));
        if (!book.isAvailable() || !book.isShareable() || book.getQuantity() == 0) {
            throw new BookNotAvailableException("Book " + bookId + " not available");
        }

        rabbitTemplate.convertAndSend(config.exchangeName(), config.borrowBookQueue(), "Book borrow request");
        System.out.println("book borrow request by user " + userId);

        book.setUser(userId);
        return DtoMapper.toDto(bookRepo.save(book));
    }

    @Override
    public BookDto returnBook(UUID userId, UUID bookId) {
        Book book = DtoMapper.toBook(getBook(bookId));
        if (!book.isAvailable() || !book.isShareable() || book.getQuantity() == 0) {
            throw new BookNotAvailableException("Book " + bookId + " not available");
        }

        rabbitTemplate.convertAndSend(config.exchangeName(), config.borrowBookQueue(), "Book return request");
        System.out.println("book return request by user " + userId);

        book.setUser(null);
        return DtoMapper.toDto(bookRepo.save(book));
    }
}
