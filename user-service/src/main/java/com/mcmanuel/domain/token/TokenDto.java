package com.mcmanuel.domain.token;


import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record TokenDto (
        UUID tokenId,
        String token,
        LocalDateTime createdAt,
        LocalDateTime expiresAt
){
}
