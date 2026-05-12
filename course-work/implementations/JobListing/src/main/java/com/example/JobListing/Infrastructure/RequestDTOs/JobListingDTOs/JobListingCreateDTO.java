package com.example.JobListing.Infrastructure.RequestDTOs.JobListingDTOs;

import lombok.Builder;

@Builder
public record JobListingCreateDTO
    (
        String Name,
        String Description,
        Integer company_id
    )
{ }
