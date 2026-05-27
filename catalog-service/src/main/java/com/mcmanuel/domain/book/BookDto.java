package com.mcmanuel.domain.book;

import lombok.With;

import java.time.LocalDateTime;
import java.util.UUID;


public record BookDto(
        @With UUID bookId,
        @With String title,
        @With boolean available,
        @With boolean shareable,
        @With int quantity,
        @With UUID user,
        LocalDateTime createdDate
){}
