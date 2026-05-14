package com.example.JobListing.Service.Interface;

import com.example.JobListing.Infrastructure.RequestDTOs.JobApplicationDTOs.JobApplicationRequestDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.JobApplicationResponseDTO;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IJobApplicationService
{

    @Async
    CompletableFuture<List<JobApplicationResponseDTO>> GetApplicationsByUser(int user_id);
    @Async
    CompletableFuture<List<JobApplicationResponseDTO>> GetApplicationsByListing(int listing_id);
    @Async
    CompletableFuture<JobApplicationResponseDTO> GetApplication(int id);
    @Async
    CompletableFuture<JobApplicationResponseDTO> SaveApplication(JobApplicationRequestDTO entity);
    @Async
    CompletableFuture<JobApplicationResponseDTO> DeleteApplication(int id);

}
