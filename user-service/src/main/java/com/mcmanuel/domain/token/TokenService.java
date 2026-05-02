package com.mcmanuel.domain.token;

import org.springframework.stereotype.Service;

@Service
public class TokenService {
    private TokenRepository tokenRepo;

    public TokenDto saveToken(TokenDto tokenDto) {
        TokenDtoMapper.toToken(tokenDto);
        return tokenDto;
    }
}
