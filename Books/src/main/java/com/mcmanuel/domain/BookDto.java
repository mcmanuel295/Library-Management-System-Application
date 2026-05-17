package com.mcmanuel.domain;

import lombok.Setter;
import lombok.With;

import java.time.LocalDateTime;
import java.util.UUID;


@Setter
public record BookDto(
        @With UUID bookId,
        @With String title,
        @With boolean available,
        @With int quantity,
        LocalDateTime createdDate
){}
