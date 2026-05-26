package com.example.JobListing.Infrastructure.RequestDTOs.TechDTOs;

import com.example.JobListing.Entities.Enums.TechCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record TechRequestDto
        (
                @NotBlank(message = "Tech name is required")
                String name,
                @NotNull(message = "Tech category is required")
                TechCategory techCategory
        )
{ }
