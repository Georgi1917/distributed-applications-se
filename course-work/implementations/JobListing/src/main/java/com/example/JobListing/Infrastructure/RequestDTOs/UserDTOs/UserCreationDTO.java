package com.example.JobListing.Infrastructure.RequestDTOs.UserDTOs;

import com.example.JobListing.Entities.Enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserCreationDTO
    (
            @NotBlank(message = "Email is required")
            @Email(message = "Email is invalid")
            String Email,
            @NotBlank(message = "Username is required")
            @Size(min = 6, message = "Username must be at least 6 characters long")
            String Username,
            @NotBlank(message = "Password is required")
            @Size(min = 6, message = "Password must be at least 6 symbols long")
            String Password,
            @NotNull(message = "Role is required")
            UserRole Role
    )
{ }
