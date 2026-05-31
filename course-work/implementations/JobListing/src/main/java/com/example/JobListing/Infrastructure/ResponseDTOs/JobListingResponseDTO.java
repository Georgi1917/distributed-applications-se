package com.example.JobListing.Infrastructure.ResponseDTOs;

import com.example.JobListing.Entities.Enums.JobListingExperienceLevel;
import lombok.Builder;

@Builder
public record JobListingResponseDTO
    (
        Long Id,
        String Name,
        String Description,
        Double salary,
        JobListingExperienceLevel ExperienceLevel,
        Long company_id
    )
{ }
