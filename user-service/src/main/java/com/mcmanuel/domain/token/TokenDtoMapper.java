package com.mcmanuel.domain.token;

public class TokenDtoMapper {

    public static Token toToken(TokenDto dto){
        return Token.builder()
                .tokenId(dto.tokenId())
                .token(dto.token())
                .createdAt(dto.createdAt())
                .expiresAt(dto.expiresAt())
                .build();
    }

    public static TokenDto toTokenDto(Token token){
        return TokenDto.builder()
                .tokenId(token.getTokenId())
                .token(token.getToken())
                .createdAt(token.getCreatedAt())
                .expiresAt(token.getExpiresAt())
                .build();
    }
}
