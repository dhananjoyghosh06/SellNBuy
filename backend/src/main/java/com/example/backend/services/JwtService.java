package com.example.backend.services;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

   
    private final String secretKey="2c8ee7b9eab14987d8076079825c2474206935b40afb618756a0e693026b8535ec7b47b6c4c606e47ceeadfe300d56ca91535830b2afcc6e6c2d94559203c809f80fb08e08b322290e3379617308c84f411275ce7d01a2ef7d40fbc1dc9d77f8993935aba39ee2e62f38c9b1720d61593743331ccccd888c651f1c4b1edda0c072009901313fed05ecd521b96f91380a66c27fabf2a28d0adb6476527c128cf471285d97a7c959e0400e34d0847d8283c5f091c76e1b603c0161367c36d5afe0fbe3fd907e6105fb442158a4464e27b23f68f0b3a5a96c1fc06bf5d9fd2a147eccca461be055ce61fae6704249b3ec1ae1e4af7a19361509afff5ec08f7ee4af";

    public String generateToken(String email){


        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .and()
                .signWith(generateKey())
                .compact();
    }

    private SecretKey generateKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T>claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()
                    .verifyWith(generateKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();  
    }

    public boolean validateToken(String token , UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims:: getExpiration);
    }
}
