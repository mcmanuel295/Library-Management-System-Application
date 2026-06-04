package com.mcmanuel.domain.token;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Builder
class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID tokenId;

    private String token;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Future @Column(nullable = false)
    private LocalDateTime expiresAt;
}
