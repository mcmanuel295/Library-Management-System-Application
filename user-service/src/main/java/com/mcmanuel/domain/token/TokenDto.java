package com.mcmanuel.domain.token;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record TokenDto(UUID tokenId, String token, LocalDateTime createdAt, LocalDateTime expiresAt) {}
