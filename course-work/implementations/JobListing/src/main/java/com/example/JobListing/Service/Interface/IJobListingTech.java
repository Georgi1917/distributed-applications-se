package com.example.JobListing.Service.Interface;

import com.example.JobListing.Infrastructure.RequestDTOs.JobListingTechDTOs.JobListingTechRequestDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.JobListingTechResponseDTO;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IJobListingTech
{

    @Async
    CompletableFuture<List<JobListingTechResponseDTO>> GetAllByListingId(long listing_id);
    @Async
    CompletableFuture<List<JobListingTechResponseDTO>> GetAllByTechId(long tech_id);
    @Async
    CompletableFuture<JobListingTechResponseDTO> SaveListingTech(JobListingTechRequestDTO entity);
    @Async
    void DeleteListingTech(long id);

}
