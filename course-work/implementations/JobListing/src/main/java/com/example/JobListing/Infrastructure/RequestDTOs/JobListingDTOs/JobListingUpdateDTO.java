package com.example.JobListing.Infrastructure.RequestDTOs.JobListingDTOs;

import com.example.JobListing.Entities.Enums.JobListingExperienceLevel;

public record JobListingUpdateDTO
    (
        String Name,
        String Description,
        JobListingExperienceLevel ExperienceLevel
    )
{ }
