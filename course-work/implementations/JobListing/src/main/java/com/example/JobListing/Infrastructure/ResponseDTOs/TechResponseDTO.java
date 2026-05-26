package com.example.JobListing.Infrastructure.ResponseDTOs;

import com.example.JobListing.Entities.Enums.TechCategory;
import lombok.Builder;

@Builder
public record TechResponseDTO
        (
                int id,
                String name,
                TechCategory techCategory
        )
{ }
