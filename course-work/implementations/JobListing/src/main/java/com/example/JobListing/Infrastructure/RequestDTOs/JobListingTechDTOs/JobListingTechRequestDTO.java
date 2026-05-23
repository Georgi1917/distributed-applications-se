package com.example.JobListing.Infrastructure.RequestDTOs.JobListingTechDTOs;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record JobListingTechRequestDTO
    (
        @NotNull(message = "Listing Id cannot be null")
        int listing_id,
        @NotNull(message = "Tech Id cannot be null")
        int tech_id,
        @NotNull(message = "Is Required field cannot be null")
        boolean IsRequired
    )
{ }
