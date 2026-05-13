package com.example.JobListing.Infrastructure.ResponseDTOs;

import lombok.Builder;

@Builder
public record JobListingResponseDTO
    (
        Integer Id,
        String Name,
        String Description,
        Integer company_id
    )
{ }
