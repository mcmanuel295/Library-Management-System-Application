package com.mcmanuel.domain.book;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.With;

public record BookDto(
        @With UUID bookId,
        @With String title,
        @With String code,
        @With boolean available,
        @With boolean shareable,
        @With int quantity,
        @With UUID user,
        LocalDateTime createdDate,
        LocalDateTime borrowedDate) {}
