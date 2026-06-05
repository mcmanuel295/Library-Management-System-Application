package com.mcmanuel.domain.book;

import com.mcmanuel.configuration.ApplicationConfiguration;
import com.mcmanuel.exception.BookNotAvailableException;
import com.mcmanuel.exception.BookNotFoundException;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
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

            String code =bookCodeGenerator();
            while( getAllCode().contains(code)){
                code=bookCodeGenerator();
            }
            book.setCode(code);

            rabbitTemplate.convertAndSend(config.newBookQueue(),config.newBookQueue(),"new Book added");
            return DtoMapper.toDto(bookRepo.save(book));
        }

        Book book = optionalObj.get();
        book.setQuantity(book.getQuantity() + 1);
        return DtoMapper.toDto(book);
    }

    private String bookCodeGenerator(){
        String charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            builder.append(charset.charAt((int) (Math.random()*charset.length())));
        }
        return builder.toString();
    }

    public List<String> getAllCode (){
        return bookRepo.getAllCode();
    }

    @Override
    public BookDto getBook(UUID bookId) throws BookNotFoundException {
        return DtoMapper.toDto(bookRepo.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book with bookId" + bookId + " not found")));
    }

    @Override
    public BookDto getBookByCode(String code) throws BookNotFoundException {
        return DtoMapper.toDto(bookRepo.findByCode(code)
                .orElseThrow(() -> new BookNotFoundException("Book with bookId" + code + " not found")));
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
        if (!book.isAvailable() || !book.isShareable() || book.getQuantity() <= 0) {
            throw new BookNotAvailableException("Book " + bookId + " not available");
        }

        book.setQuantity(book.getQuantity()-1);
        rabbitTemplate.convertAndSend(config.exchangeName(), config.borrowBookQueue(), "Borrow Book Request");
        System.out.println("book borrow request by user " + userId);

        book.setUser(userId);
        book.setShareable(book.getQuantity() == 0);
        book.setAvailable(book.getQuantity()==0);

        return DtoMapper.toDto(bookRepo.save(book));
    }

    @Override
    public BookDto returnBook(UUID userId, UUID bookId) {
        Book book = DtoMapper.toBook(getBook(bookId));
        if (!book.isAvailable() || !book.isShareable() || book.getQuantity() <= 0) {
            throw new BookNotAvailableException("Book " + bookId + " not available");
        }
        book.setQuantity(book.getQuantity()+1);

        rabbitTemplate.convertAndSend(config.exchangeName(), config.borrowBookQueue(), "Book return request");
        System.out.println("book return request by user " + userId);

        book.setUser(userId);
        book.setShareable(book.getQuantity() == 0);
        book.setAvailable(book.getQuantity()==0);

        book.setUser(null);
        return DtoMapper.toDto(bookRepo.save(book));
    }
}
