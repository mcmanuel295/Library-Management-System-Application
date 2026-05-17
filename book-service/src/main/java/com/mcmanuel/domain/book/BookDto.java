package com.mcmanuel.domain.book;


import java.time.LocalDateTime;
import java.util.UUID;

public record BookDto(
        UUID bookId,
        String title,
        String isbn,
        boolean available,
        LocalDateTime createdAt

) {
}
