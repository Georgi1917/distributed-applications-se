package com.example.JobListing.AuthService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService implements IJwtService
{

    private final String secretKey = "f1d42a0caabbbbea2588fc370e92e9f31deb786f7e68c989cd3d3569c87d97fd";
    private final SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

    public String GenerateToken(String Username)
    {

        return Jwts.builder()
                .subject(Username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

    }

    public String ExtractUsername(String Token)
    {

        return GetClaims(Token).getSubject();

    }

    public boolean IsTokenValid(String Token)
    {

        try {
            GetClaims(Token);
            return true;
        } catch (Exception e){
            return false;
        }

    }

    private Claims GetClaims(String Token)
    {

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(Token)
                .getPayload();

    }

}
