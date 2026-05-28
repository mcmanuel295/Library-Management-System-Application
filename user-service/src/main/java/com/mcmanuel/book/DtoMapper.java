package com.mcmanuel.book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class DtoMapper {
    public static BookDto toDto(Book book) {
        return new BookDto(
                book.getBookId(),
                book.getTitle(),
                book.isAvailable(),
                book.isShareable(),
                book.getQuantity(),
                book.getUser(),
                book.getCreatedDate(),
                book.getBorrowedDate()
        );
    }

    public static Book toBook(BookDto updatedBook) {
        return new Book(
                updatedBook.bookId(),
                updatedBook.title(),
                updatedBook.available(),
                updatedBook.shareable(),
                updatedBook.quantity(),
                updatedBook.user(),
                updatedBook.createdDate(),
                updatedBook.borrowedDate()
        );
    }
}
