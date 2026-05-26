package com.example.JobListing.Service.Implementation;

import com.example.JobListing.Entities.Company;
import com.example.JobListing.Entities.JobListing;
import com.example.JobListing.Exception.ElementNotFound;
import com.example.JobListing.Infrastructure.RequestDTOs.JobListingDTOs.JobListingCreateDTO;
import com.example.JobListing.Infrastructure.RequestDTOs.JobListingDTOs.JobListingUpdateDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.JobListingResponseDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.UserResponseDTO;
import com.example.JobListing.Repository.CompanyRepository;
import com.example.JobListing.Repository.JobListingRepository;
import com.example.JobListing.Service.Interface.IJobListingService;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final JobListingRepository listing_repository;

    public JobListingService(JobListingRepository repository, CompanyRepository company_repository)
    {
        super(repository);
        listing_repository = repository;
        _company_repository = company_repository;
    }

    @Async
    public CompletableFuture<Page<JobListingResponseDTO>> GetAllListings
            (Pageable pageable, @Nullable String searchBy)
    {

        return CompletableFuture.completedFuture(listing_repository.findBySearchParam(searchBy, pageable))
                .thenApply(
                        page -> page.map(
                                item -> JobListingResponseDTO.builder()
                                        .Id(item.getId())
                                        .Name(item.getName())
                                        .Description(item.getDescription())
                                        .ExperienceLevel(item.getExperienceLevel())
                                        .company_id(item.getCompany().getId()).build())
                );

    }

    @Async
    public CompletableFuture<Page<JobListingResponseDTO>> GetListingsByTech
            (Pageable pageable, int tech_id, @Nullable String searchBy)
    {

        return CompletableFuture.completedFuture(listing_repository
                .findByJobListingTech_Tech_Id(tech_id, searchBy, pageable).map(
                item -> JobListingResponseDTO.builder()
                        .Id(item.getId())
                        .Name(item.getName())
                        .Description(item.getDescription())
                        .ExperienceLevel(item.getExperienceLevel())
                        .company_id(item.getCompany().getId()).build()
        ));

    }

    @Async
    public CompletableFuture<Page<JobListingResponseDTO>> GetListingsByUser
            (Pageable pageable, int user_id, @Nullable String searchBy)
    {

        return CompletableFuture.completedFuture(listing_repository
                .findByUser_User_Id(user_id, searchBy, pageable).map(
                item -> JobListingResponseDTO.builder()
                        .Id(item.getId())
                        .Name(item.getName())
                        .Description(item.getDescription())
                        .ExperienceLevel(item.getExperienceLevel())
                        .company_id(item.getCompany().getId()).build()
        ));

    }

    @Async
    public CompletableFuture<Page<JobListingResponseDTO>> GetListingsByCompany
            (Pageable pageable, int company_id, @Nullable String searchBy)
    {

        return CompletableFuture.completedFuture(listing_repository.
                findByCompany(company_id, searchBy, pageable).map(
                        item -> JobListingResponseDTO.builder()
                                .Id(item.getId())
                                .Name(item.getName())
                                .Description(item.getDescription())
                                .ExperienceLevel(item.getExperienceLevel())
                                .company_id(item.getCompany().getId()).build()
                ));

    }

    @Async
    public CompletableFuture<JobListingResponseDTO> GetListing(int id)
    {

        return super.GetItem(id).thenApply(
                item -> JobListingResponseDTO.builder()
                        .Id(item.getId())
                        .Name(item.getName())
                        .Description(item.getDescription())
                        .ExperienceLevel(item.getExperienceLevel())
                        .company_id(item.getCompany().getId()).build());

    }

    @Async
    public CompletableFuture<JobListingResponseDTO> SaveListing(@RequestBody JobListingCreateDTO entity)
    {

        Company company = _company_repository.findById(entity.company_id()).orElseThrow(
                () -> new ElementNotFound("Company does not exist.")
        );
        JobListing item = JobListing.builder()
                                    .name(entity.Name())
                                    .description(entity.Description())
                                    .experienceLevel(entity.ExperienceLevel())
                                    .company(company).build();

        super.Save(item);

        return CompletableFuture.completedFuture(JobListingResponseDTO.builder()
                            .Id(item.getId())
                            .Name(item.getName())
                            .Description(item.getDescription())
                            .ExperienceLevel(item.getExperienceLevel())
                            .company_id(item.getCompany().getId()).build());

    }

    @Async
    public CompletableFuture<JobListingResponseDTO> UpdateListing
            (@PathVariable int id, @RequestBody JobListingUpdateDTO entity)
    {

        return super.GetItem(id).thenApply(
                item -> {

                    item.setName(entity.Name());
                    item.setDescription(entity.Description());
                    item.setExperienceLevel(entity.ExperienceLevel());

                    super.Save(item);

                    return item;

                }
        ).thenApply(
                item -> JobListingResponseDTO.builder()
                        .Id(item.getId())
                        .Name(item.getName())
                        .Description(item.getDescription())
                        .ExperienceLevel(item.getExperienceLevel())
                        .company_id(item.getCompany().getId()).build());

    }

    @Async
    public CompletableFuture<JobListingResponseDTO> DeleteListing(@PathVariable int id)
    {

        return super.Delete(id).thenApply(
                item -> JobListingResponseDTO.builder()
                        .Id(item.getId())
                        .Name(item.getName())
                        .Description(item.getDescription())
                        .ExperienceLevel(item.getExperienceLevel())
                        .company_id(item.getCompany().getId()).build()
        );

    }

}
