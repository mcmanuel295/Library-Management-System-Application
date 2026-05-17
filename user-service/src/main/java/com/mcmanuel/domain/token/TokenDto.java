package com.mcmanuel.domain.token;


import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TokenDto (
        Integer tokenId,
        String token,
        LocalDateTime createdAt,
        LocalDateTime expiresAt
){
}
