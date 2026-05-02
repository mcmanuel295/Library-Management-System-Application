package com.mcmanuel.domain.token;

import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Builder
public class Token {
    private Integer tokenId;

    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
}
