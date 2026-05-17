package com.mcmanuel.domain.token;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepo;

    public TokenDto saveToken(TokenDto tokenDto) {
        tokenRepo.save(TokenMapper.toToken(tokenDto));
        return tokenDto;
    }
}
