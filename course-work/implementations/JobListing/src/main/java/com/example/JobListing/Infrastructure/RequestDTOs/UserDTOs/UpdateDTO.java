package com.example.JobListing.Infrastructure.RequestDTOs.UserDTOs;

import lombok.*;

@Builder
public record UpdateDTO
    (
        String Username,
        String Email
    )
{ }
