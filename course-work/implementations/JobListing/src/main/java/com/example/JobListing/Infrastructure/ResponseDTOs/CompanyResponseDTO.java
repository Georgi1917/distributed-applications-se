package com.example.JobListing.Infrastructure.ResponseDTOs;

import com.example.JobListing.Entities.Enums.CompanyType;
import com.example.JobListing.Entities.Enums.RemotePolicy;
import lombok.Builder;

@Builder
public record CompanyResponseDTO
        (
            Integer Id,
            String CompanyName,
            String Description,
            Integer EmployeeCount,
            CompanyType Type,
            RemotePolicy RemotePolicy,
            boolean IsHiring
        )
{ }
