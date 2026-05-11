package com.example.JobListing.Infrastructure.RequestDTOs.CompanyDTOs;

import com.example.JobListing.Entities.Enums.CompanyType;
import com.example.JobListing.Entities.Enums.RemotePolicy;
import lombok.Builder;

@Builder
public record CompanyRequestDTO
        (
            String CompanyName,
            String Description,
            Integer EmployeeCount,
            CompanyType Type,
            RemotePolicy RemotePolicy,
            boolean IsHiring
        )
{ }
