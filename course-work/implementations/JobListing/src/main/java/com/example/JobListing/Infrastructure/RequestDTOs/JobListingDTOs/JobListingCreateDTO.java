package com.example.JobListing.Infrastructure.RequestDTOs.JobListingDTOs;

import com.example.JobListing.Entities.Enums.JobListingExperienceLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record JobListingCreateDTO
    (
        @NotBlank(message = "Listing Name cannot be blank")
        @Size(min = 3, max = 60)
        String Name,

        @NotBlank(message = "Listing Description cannot be blank")
        @Size(max = 2000)
        String Description,

        @NotNull(message = "Salary cannot be null")
        Double salary,

        @NotNull(message = "Listing experience level cannot be blank")
        JobListingExperienceLevel ExperienceLevel,

        @NotNull(message = "Company Id cannot be null")
        Long company_id
    )
{ }
