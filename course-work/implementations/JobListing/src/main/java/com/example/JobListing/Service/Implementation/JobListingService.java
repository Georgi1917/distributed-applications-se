package com.example.JobListing.Service.Implementation;

import com.example.JobListing.Entities.Company;
import com.example.JobListing.Entities.JobListing;
import com.example.JobListing.Infrastructure.RequestDTOs.JobListingDTOs.JobListingCreateDTO;
import com.example.JobListing.Infrastructure.RequestDTOs.JobListingDTOs.JobListingUpdateDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.JobListingResponseDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.UserResponseDTO;
import com.example.JobListing.Repository.CompanyRepository;
import com.example.JobListing.Repository.JobListingRepository;
import com.example.JobListing.Service.Interface.IJobListingService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class JobListingService extends BaseService<JobListing> implements IJobListingService
{

    private final CompanyRepository _company_repository;

    public JobListingService(JobListingRepository repository, CompanyRepository company_repository)
    {
        super(repository);
        _company_repository = company_repository;
    }

    @Async
    public CompletableFuture<List<JobListingResponseDTO>> GetAllListings()
    {

        CompletableFuture<List<JobListing>> future = super.GetAll();
        List<JobListing> items = future.join();

        return CompletableFuture.completedFuture(items.stream()
                                            .map(item -> JobListingResponseDTO.builder()
                                                    .Name(item.getName())
                                                    .Description(item.getDescription())
                                                    .company_id(item.getCompany().getId()).build()).toList());

    }

    @Async
    public CompletableFuture<JobListingResponseDTO> GetListing(int id)
    {

        CompletableFuture<JobListing> future = super.GetItem(id);
        JobListing item = future.join();

        return CompletableFuture.completedFuture(JobListingResponseDTO.builder()
                            .Name(item.getName())
                            .Description(item.getDescription())
                            .company_id(item.getCompany().getId()).build());

    }

    @Async
    public CompletableFuture<JobListingResponseDTO> SaveListing(@RequestBody JobListingCreateDTO entity)
    {

        Company company = _company_repository.findById(entity.company_id()).orElseThrow();
        JobListing item = JobListing.builder()
                                    .Name(entity.Name())
                                    .Description(entity.Description())
                                    .Company(company).build();

        super.Save(item);

        return CompletableFuture.completedFuture(JobListingResponseDTO.builder()
                            .Name(item.getName())
                            .Description(item.getDescription())
                            .company_id(item.getCompany().getId()).build());

    }

    @Async
    public CompletableFuture<JobListingResponseDTO> UpdateListing
            (@PathVariable int id, @RequestBody JobListingUpdateDTO entity)
    {

        CompletableFuture<JobListing> future = super.GetItem(id);
        JobListing item = future.join();

        item.setName(entity.Name());
        item.setDescription(entity.Description());

        super.Save(item);

        return CompletableFuture.completedFuture(JobListingResponseDTO.builder()
                .Name(item.getName())
                .Description(item.getDescription())
                .company_id(item.getCompany().getId()).build());

    }

    @Async
    public CompletableFuture<JobListingResponseDTO> DeleteListing(@PathVariable int id)
    {

        CompletableFuture<JobListing> future = super.Delete(id);
        JobListing item = future.join();

        return CompletableFuture.completedFuture(JobListingResponseDTO.builder()
                .Name(item.getName())
                .Description(item.getDescription())
                .company_id(item.getCompany().getId()).build());

    }

    @Override
    protected void UpdateEntity(JobListing existing, JobListing updated)
    {



    }

}
