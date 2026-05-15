package com.example.JobListing.Infrastructure.RequestDTOs.JobListingDTOs;

import com.example.JobListing.Entities.Enums.JobListingExperienceLevel;
import lombok.Builder;

@Builder
public record JobListingCreateDTO
    (
        String Name,
        String Description,
        JobListingExperienceLevel ExperienceLevel,
        Integer company_id
    )
{ }
