package com.example.JobListing.Infrastructure.ResponseDTOs;

import com.example.JobListing.Entities.Enums.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public record UserResponseDTO
    (
            Long Id,
            String Email,
            String Username,
            UserRole Role
    )
{ }
