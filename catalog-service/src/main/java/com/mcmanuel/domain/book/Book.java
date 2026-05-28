package com.mcmanuel.domain.book;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@ToString
class Book{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "book_id", updatable = false, nullable = false)
    private UUID bookId;

    @Column(nullable = false)
    @NotBlank(message = "this field cannot be blank")
    private String title;

    private boolean available;
    private boolean shareable;

    @Min(value = 0,message = "this field cannot be blank")
    private int quantity;

    private UUID user;

    @Column(nullable = false,updatable = false)
    private LocalDateTime createdDate;

    private LocalDateTime borrowedDate;
}
