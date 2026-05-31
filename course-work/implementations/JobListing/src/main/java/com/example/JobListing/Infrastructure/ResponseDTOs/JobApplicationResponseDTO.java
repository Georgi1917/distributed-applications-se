package com.example.JobListing.Infrastructure.ResponseDTOs;

import lombok.Builder;

@Builder
public record JobApplicationResponseDTO
    (
        Long Id,
        Long user_id,
        Long listing_id
    )
{ }
