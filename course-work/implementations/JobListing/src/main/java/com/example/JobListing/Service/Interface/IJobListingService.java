package com.example.JobListing.Service.Interface;

import com.example.JobListing.Infrastructure.RequestDTOs.JobListingDTOs.JobListingCreateDTO;
import com.example.JobListing.Infrastructure.RequestDTOs.JobListingDTOs.JobListingUpdateDTO;
import com.example.JobListing.Infrastructure.RequestDTOs.UserDTOs.RegisterDTO;
import com.example.JobListing.Infrastructure.RequestDTOs.UserDTOs.UpdateDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.JobListingResponseDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.UserResponseDTO;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IJobListingService
{

    @Async
    CompletableFuture<Page<JobListingResponseDTO>> GetAllListings
            (Pageable pageable, @Nullable String searchBy);
    @Async
    CompletableFuture<Page<JobListingResponseDTO>> GetListingsByTech
            (Pageable pageable, int tech_id, @Nullable String searchBy);
    @Async
    CompletableFuture<Page<JobListingResponseDTO>> GetListingsByUser
            (Pageable pageable, int user_id, @Nullable String searchBy);
    @Async
    CompletableFuture<JobListingResponseDTO> GetListing(int id);
    @Async
    CompletableFuture<JobListingResponseDTO> SaveListing(JobListingCreateDTO entity);
    @Async
    CompletableFuture<JobListingResponseDTO> UpdateListing(int id, JobListingUpdateDTO entity);
    @Async
    CompletableFuture<JobListingResponseDTO> DeleteListing(int id);

}
