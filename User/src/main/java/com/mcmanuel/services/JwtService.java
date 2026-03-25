package com.mcmanuel.services;

import com.mcmanuel.entities.User;
import com.mcmanuel.pojo.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;


@Service
public class JwtService {
    String secretString;
    public JwtService(){
        KeyGenerator keyGen = null;
        try {
            keyGen = KeyGenerator.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error initializing JWT KeyGenerator", e);
        }
        SecretKey secretKey = keyGen.generateKey();
        secretString =Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public SecretKey key(){
        byte[] bytes = Base64.getDecoder().decode(this.secretString);
        return Keys.hmacShaKeyFor(bytes);
    }

    public String generateToken(User user){
        Map<String,Object> claims = new HashMap<>();

        List<Role> roles = user.getRoles();
        claims.put("roles",roles);

        return Jwts.builder()
                .claims()
                .add(claims)
                .add("authorities", List.of(user.getRoles()))
                .and()
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+(5*60*1000)))
                .signWith(key())
                .compact();
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

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
