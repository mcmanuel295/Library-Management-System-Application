package com.mcmanuel.domain;

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
                book.getCreatedDate()
        );
    }

    public static Book toBook(BookDto updatedBook) {
        return new Book(
                updatedBook.bookId(),
                updatedBook.title(),
                updatedBook.available(),
                updatedBook.shareable(),
                updatedBook.quantity(),
                updatedBook.createdDate()
        );
    }
}
