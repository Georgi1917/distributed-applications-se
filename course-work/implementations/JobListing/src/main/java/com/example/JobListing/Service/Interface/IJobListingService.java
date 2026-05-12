package com.example.JobListing.Service.Interface;

import com.example.JobListing.Infrastructure.RequestDTOs.JobListingDTOs.JobListingCreateDTO;
import com.example.JobListing.Infrastructure.RequestDTOs.JobListingDTOs.JobListingUpdateDTO;
import com.example.JobListing.Infrastructure.RequestDTOs.UserDTOs.RegisterDTO;
import com.example.JobListing.Infrastructure.RequestDTOs.UserDTOs.UpdateDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.JobListingResponseDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.UserResponseDTO;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IJobListingService
{

    @Async
    public CompletableFuture<List<JobListingResponseDTO>> GetAllListings();
    @Async
    public CompletableFuture<JobListingResponseDTO> GetListing(int id);
    @Async
    public CompletableFuture<JobListingResponseDTO> SaveListing(JobListingCreateDTO entity);
    @Async
    public CompletableFuture<JobListingResponseDTO> UpdateListing(int id, JobListingUpdateDTO entity);
    @Async
    public CompletableFuture<JobListingResponseDTO> DeleteListing(int id);

}
