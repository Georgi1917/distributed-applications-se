package com.example.JobListing.Infrastructure.ResponseDTOs;

import lombok.Builder;

@Builder
public record JobListingTechResponseDTO
    (
        int id,
        int listing_id,
        int tech_id,
        boolean IsRequired
    )
{ }
