package com.mcmanuel.domain;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private UUID bookId;

    @Column(nullable = false)
    @NotBlank(message = "this field cannot be blank")
    private String title;

    private boolean available;
    private boolean shareable;

    @NotBlank(message = "this field cannot be blank")
    @Min(value = 0)
    private int quantity;

    @Column(nullable = false,updatable = false)
    private LocalDateTime createdDate;

    private UUID user;

}
