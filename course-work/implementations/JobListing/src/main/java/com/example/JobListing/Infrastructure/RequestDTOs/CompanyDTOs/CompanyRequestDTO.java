package com.example.JobListing.Infrastructure.RequestDTOs.CompanyDTOs;

import com.example.JobListing.Entities.Enums.CompanyType;
import com.example.JobListing.Entities.Enums.CompanyRemotePolicy;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CompanyRequestDTO
        (
            @NotBlank(message = "Name cannot be blank")
            @Size(min = 3, max = 60)
            String CompanyName,
            @NotBlank(message = "Description cannot be blank")
            @Size(max = 2000)
            String Description,
            Integer EmployeeCount,
            @NotNull(message = "Type cannot be blank")
            CompanyType Type,
            @NotNull(message = "Remote policy cannot be blank")
            CompanyRemotePolicy CompanyRemotePolicy,
            @NotNull(message = "Hiring cannot be empty")
            boolean IsHiring
        )
{ }
