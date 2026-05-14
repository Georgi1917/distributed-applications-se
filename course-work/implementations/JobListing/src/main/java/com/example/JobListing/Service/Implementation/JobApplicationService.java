package com.example.JobListing.Service.Implementation;

import com.example.JobListing.Entities.JobApplication;
import com.example.JobListing.Entities.JobListing;
import com.example.JobListing.Entities.User;
import com.example.JobListing.Infrastructure.RequestDTOs.JobApplicationDTOs.JobApplicationRequestDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.JobApplicationResponseDTO;
import com.example.JobListing.Repository.JobApplicationRepository;
import com.example.JobListing.Repository.JobListingRepository;
import com.example.JobListing.Repository.UserRepository;
import com.example.JobListing.Service.Interface.IJobApplicationService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class JobApplicationService extends BaseService<JobApplication> implements IJobApplicationService
{

    private final UserRepository _user_repository;
    private final JobListingRepository _listing_repository;

    public JobApplicationService
            (JobApplicationRepository repository, UserRepository user_repository,
             JobListingRepository listing_repository)
    {

        super(repository);
        _user_repository = user_repository;
        _listing_repository = listing_repository;

    }

    @Async
    public CompletableFuture<List<JobApplicationResponseDTO>> GetApplicationsByUser(int user_id)
    {

        CompletableFuture<List<JobApplication>> future = super.GetAll();
        List<JobApplication> items = future.join().stream()
                .filter(item -> item.getApplicant().getId() == user_id).toList();

        return CompletableFuture.completedFuture(items.stream().map(item ->
                JobApplicationResponseDTO.builder()
                        .Id(item.getId())
                        .user_id(item.getApplicant().getId())
                        .listing_id(item.getJobListing().getId()).build()).toList());

    }

    @Async
    public CompletableFuture<List<JobApplicationResponseDTO>> GetApplicationsByListing(int listing_id)
    {

        CompletableFuture<List<JobApplication>> future = super.GetAll();
        List<JobApplication> items = future.join().stream()
                .filter(item -> item.getJobListing().getId() == listing_id).toList();

        return CompletableFuture.completedFuture(items.stream().map(item ->
                JobApplicationResponseDTO.builder()
                        .Id(item.getId())
                        .user_id(item.getApplicant().getId())
                        .listing_id(item.getJobListing().getId()).build()).toList());

    }

    @Async
    public CompletableFuture<JobApplicationResponseDTO> GetApplication(int id)
    {

        CompletableFuture<JobApplication> future = super.GetItem(id);
        JobApplication item = future.join();

        return CompletableFuture.completedFuture(JobApplicationResponseDTO.builder()
                .Id(item.getId())
                .user_id(item.getApplicant().getId())
                .listing_id(item.getJobListing().getId()).build());

    }

    @Async
    public CompletableFuture<JobApplicationResponseDTO> SaveApplication(JobApplicationRequestDTO entity)
    {

        User needed_user = _user_repository.findById(entity.user_id()).orElseThrow();
        JobListing needed_listing = _listing_repository.findById(entity.listing_id()).orElseThrow();

        JobApplication item = JobApplication.builder()
                .Applicant(needed_user)
                .JobListing(needed_listing).build();

        super.Save(item);

        return CompletableFuture.completedFuture(JobApplicationResponseDTO.builder()
                .Id(item.getId())
                .user_id(item.getApplicant().getId())
                .listing_id(item.getJobListing().getId()).build());

    }

    @Async
    public CompletableFuture<JobApplicationResponseDTO> DeleteApplication(int id)
    {

        CompletableFuture<JobApplication> future = super.Delete(id);
        JobApplication item = future.join();

        return CompletableFuture.completedFuture(JobApplicationResponseDTO.builder()
                .Id(item.getId())
                .user_id(item.getApplicant().getId())
                .listing_id(item.getJobListing().getId()).build());

    }

    @Override
    protected void UpdateEntity(JobApplication existing, JobApplication updated)
    {



    }

}
