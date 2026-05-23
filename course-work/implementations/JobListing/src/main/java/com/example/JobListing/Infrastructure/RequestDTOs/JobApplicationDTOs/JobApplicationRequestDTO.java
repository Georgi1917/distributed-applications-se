package com.example.JobListing.Infrastructure.RequestDTOs.JobApplicationDTOs;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record JobApplicationRequestDTO
    (

        @NotNull(message = "User Id cannot be null")
        Integer user_id,
        @NotNull(message = "Listing Id cannot be null")
        Integer listing_id

    )
{ }
