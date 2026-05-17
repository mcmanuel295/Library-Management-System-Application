package com.mcmanuel.domain;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.UUID;

public record BookDto(
        UUID bookId,
        @Column(nullable = false)
        @NotBlank(message = "this field cannot be blank")
        String title,
        boolean available,
        @NotBlank(message = "this field cannot be blank")
        @Min(value = 0)
        int quantity,
        @Column(nullable = false,updatable = false)
        LocalDateTime createdDate
){}
