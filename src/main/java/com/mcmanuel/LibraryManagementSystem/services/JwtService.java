package com.mcmanuel.LibraryManagementSystem.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;


@Service
public class JwtService {
    String secretString;
    public JwtService() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("Hmac Sha256");
        SecretKey secretKey = keyGen.generateKey();
        secretString =Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public SecretKey key(){
        byte[] bytes = secretString.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(bytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token,Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimResolver){
        return claimResolver.apply(extractClaims(token));
    }

    public Claims extractClaims(String token){
        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    public boolean validate(String token, UserDetails userDetails) {
        return (userDetails.getUsername().equals(extractUsername(token)) && extractDate(token).after(new Date(System.currentTimeMillis())));
    }

    private Date extractDate(String token){
        return extractClaim(token,Claims::getExpiration);
    }
}
