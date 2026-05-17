package com.mcmanuel.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoMapper {
    public static BookDto Dto(Book book) {
        return new BookDto(
                book.getBookId(),
                book.getTitle(),
                book.isAvailable(),
                book.getQuantity(),
                book.getCreatedDate()
        );
    }
}
