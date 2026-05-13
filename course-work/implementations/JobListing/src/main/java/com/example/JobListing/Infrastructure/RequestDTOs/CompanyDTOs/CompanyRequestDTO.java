package com.example.JobListing.Infrastructure.RequestDTOs.CompanyDTOs;

import com.example.JobListing.Entities.Enums.CompanyType;
import com.example.JobListing.Entities.Enums.CompanyRemotePolicy;
import lombok.Builder;

@Builder
public record CompanyRequestDTO
        (
            String CompanyName,
            String Description,
            Integer EmployeeCount,
            CompanyType Type,
            CompanyRemotePolicy CompanyRemotePolicy,
            boolean IsHiring
        )
{ }
