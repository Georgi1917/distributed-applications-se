package com.example.JobListing.AuthService;

import io.jsonwebtoken.security.Keys;

import java.security.Key;

public interface IJwtService
{

    String GenerateToken(String Username);
    String ExtractUsername(String Token);
    boolean IsTokenValid(String Token);

}
