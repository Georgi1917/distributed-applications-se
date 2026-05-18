package com.example.JobListing.AuthService;

import io.jsonwebtoken.security.Keys;

import java.security.Key;

public interface IJwtService
{

    String secretKey = "MyUltraMegaSuperSecretKey123";
    Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

    String GenerateToken(String Username);
    String ExtractUsername(String Token);
    boolean IsTokenValid(String Token);

}
