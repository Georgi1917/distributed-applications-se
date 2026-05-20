package com.example.JobListing.Infrastructure.RequestDTOs.UserDTOs;

import com.example.JobListing.Entities.Enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
public record UpdateDTO
    (
        @NotBlank(message = "Email is required")
        @Email(message = "Email is invalid")
        String email,
        @NotBlank(message = "Username is required")
        @Size(min = 6, message = "Username must be at least 6 characters long")
        String username,
        @NotNull(message = "Role is required")
        UserRole role
    )
{ }
