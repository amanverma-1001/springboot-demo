package com.example.demo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTUtil {
    private final String SECRET= "my name is Aman number is 969393346212344 and it is random";
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());
    private final int EXPIRATION_TIME= 1000*60*60;
    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractClaimes(String token){
        return   Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUserName(String token) {
        Claims body = extractClaimes(token);
        return body.getSubject();
    }

    public boolean isTokenExpired(String token){
        return extractClaimes(token).getExpiration().before(new Date());
    }


    public boolean validateToken(String username, UserDetails userDetails, String token) {
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
