package com.example.JobListing.Infrastructure.ResponseDTOs;

import lombok.Builder;

@Builder
public record JobListingTechResponseDTO
    (
        Long id,
        Long listing_id,
        Long tech_id,
        boolean IsRequired
    )
{ }
