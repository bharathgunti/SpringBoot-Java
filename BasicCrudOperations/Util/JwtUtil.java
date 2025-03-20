package com.example.BasicCrudOperations.Util;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.KeyStore;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {

    SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long expiration=1000*60*60;

    public String generateToken(UserDetails userDetails){
        Map<String,Object>claims=new HashMap<>();
        return CreateToken(claims,userDetails.getUsername());
    }

    private String CreateToken(Map<String, Object> claims, String username) {

        return Jwts.builder()
                .setSubject(username)
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis()+expiration))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,key)
                .compact();
    }

    public String extractUserName(String token){
        Claims claims=extractAllClaims(token);
        return claims.getSubject();
    }

    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token,UserDetails userDetails){
        return extractUserName(token).equals(userDetails.getUsername())&&!isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

}
