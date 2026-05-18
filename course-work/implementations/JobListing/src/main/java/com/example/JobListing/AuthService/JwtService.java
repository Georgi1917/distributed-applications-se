package com.example.JobListing.AuthService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService implements IJwtService
{

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
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(Token)
                .getPayload();

    }

}
