package com.example.JobListing.Infrastructure.ResponseDTOs;

import com.example.JobListing.Entities.Enums.JobListingExperienceLevel;
import lombok.Builder;

@Builder
public record JobListingResponseDTO
    (
        Integer Id,
        String Name,
        String Description,
        JobListingExperienceLevel ExperienceLevel,
        Integer company_id
    )
{ }
