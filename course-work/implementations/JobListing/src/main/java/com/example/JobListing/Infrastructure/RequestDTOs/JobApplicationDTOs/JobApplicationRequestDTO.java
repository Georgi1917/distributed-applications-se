package com.example.JobListing.Infrastructure.RequestDTOs.JobApplicationDTOs;

import lombok.Builder;

@Builder
public record JobApplicationRequestDTO
    (

        Integer user_id,
        Integer listing_id

    )
{ }
