package com.example.JobListing.Infrastructure.RequestDTOs.UserDTOs;

import lombok.*;

@Builder
public record RegisterDTO
    (
        String Email,
        String Username,
        String Password
    )
{ }
