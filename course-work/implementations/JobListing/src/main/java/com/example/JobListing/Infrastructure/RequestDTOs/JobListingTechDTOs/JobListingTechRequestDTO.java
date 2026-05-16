package com.example.JobListing.Infrastructure.RequestDTOs.JobListingTechDTOs;

import lombok.Builder;

@Builder
public record JobListingTechRequestDTO
    (
        int listing_id,
        int tech_id,
        boolean IsRequired
    )
{ }
