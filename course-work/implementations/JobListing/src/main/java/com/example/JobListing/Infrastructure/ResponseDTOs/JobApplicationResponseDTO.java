package com.example.JobListing.Infrastructure.ResponseDTOs;

import lombok.Builder;

@Builder
public record JobApplicationResponseDTO
    (
        Integer Id,
        Integer user_id,
        Integer listing_id
    )
{ }
