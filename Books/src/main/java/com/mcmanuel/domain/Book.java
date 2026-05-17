package com.mcmanuel.domain;

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
class Book{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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
}
