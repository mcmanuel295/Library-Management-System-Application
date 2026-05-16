package com.mcmanuel.domain.book;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
class Books {
    @Id
    @GeneratedValue
    private UUID bookId;

    @Column(nullable = false,name = "book")
    private String name;
    private String isbn;

    @CreatedDate
    private LocalDateTime createdAt;

}