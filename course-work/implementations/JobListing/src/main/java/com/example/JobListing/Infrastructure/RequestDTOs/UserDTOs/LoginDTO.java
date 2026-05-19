package com.example.JobListing.Infrastructure.RequestDTOs.UserDTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record LoginDTO
    (
        @NotBlank(message = "Username is required")
        @Size(min = 6, message = "Username must be at least 6 characters long")
        String Username,
        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 symbols long")
        String Password
    )
{ }
