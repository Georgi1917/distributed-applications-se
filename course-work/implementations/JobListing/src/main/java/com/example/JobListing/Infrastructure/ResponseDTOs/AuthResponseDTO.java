package com.example.JobListing.Infrastructure.ResponseDTOs;

import lombok.Builder;

@Builder
public record AuthResponseDTO
    (
        String token
    )
{ }
