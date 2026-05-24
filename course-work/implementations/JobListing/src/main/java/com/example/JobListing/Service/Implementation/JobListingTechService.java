package com.example.JobListing.Service.Implementation;

import com.example.JobListing.Entities.JobListing;
import com.example.JobListing.Entities.JobListingTech;
import com.example.JobListing.Entities.Tech;
import com.example.JobListing.Infrastructure.RequestDTOs.JobListingTechDTOs.JobListingTechRequestDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.JobListingTechResponseDTO;
import com.example.JobListing.Repository.JobListingRepository;
import com.example.JobListing.Repository.JobListingTechRepository;
import com.example.JobListing.Repository.TechRepository;
import com.example.JobListing.Service.Interface.IJobListingService;
import com.example.JobListing.Service.Interface.IJobListingTech;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class JobListingTechService extends BaseService<JobListingTech> implements IJobListingTech
{

    private final JobListingRepository _job_listing_repo;
    private final TechRepository _tech_repo;

    public JobListingTechService
            (JobListingTechRepository repository,
             JobListingRepository listing_repo,
             TechRepository tech_repo)
    {

        super(repository);
        _job_listing_repo = listing_repo;
        _tech_repo = tech_repo;

    }

    @Async
    public CompletableFuture<List<JobListingTechResponseDTO>> GetAllByListingId(int listing_id)
    {

        return super.GetAll().thenApply(
                items ->
                        items.stream()
                                .filter(item ->
                                        item.getListing().getId() == listing_id).toList()
        ).thenApply(
                items -> items.stream().map(
                        item -> JobListingTechResponseDTO
                                .builder()
                                .id(item.getId())
                                .tech_id(item.getTech().getId())
                                .listing_id(item.getListing().getId())
                                .IsRequired(item.isIsRequired()).build()
        ).toList());

    }

    @Async
    public CompletableFuture<List<JobListingTechResponseDTO>> GetAllByTechId(int tech_id)
    {

        return super.GetAll().thenApply(
                items ->
                        items.stream()
                                .filter(item ->
                                        item.getTech().getId() == tech_id).toList()
        ).thenApply(
                items -> items.stream().map(
                        item -> JobListingTechResponseDTO
                                .builder()
                                .id(item.getId())
                                .tech_id(item.getTech().getId())
                                .listing_id(item.getListing().getId())
                                .IsRequired(item.isIsRequired()).build()
                ).toList());

    }

    @Async
    public CompletableFuture<JobListingTechResponseDTO> SaveListingTech(JobListingTechRequestDTO entity)
    {

        JobListing job_listing = _job_listing_repo.findById(entity.listing_id()).orElseThrow();
        Tech tech = _tech_repo.findById(entity.tech_id()).orElseThrow();

        JobListingTech item = JobListingTech.builder()
                .listing(job_listing)
                .tech(tech)
                .IsRequired(entity.IsRequired()).build();

        super.Save(item);

        return CompletableFuture.completedFuture(JobListingTechResponseDTO.builder()
                .id(item.getId())
                .tech_id(item.getTech().getId())
                .listing_id(item.getListing().getId())
                .IsRequired(item.isIsRequired()).build());

    }

    @Async
    public void DeleteListingTech(int id)
    {

        super.Delete(id);

    }

    @Override
    protected void UpdateEntity(JobListingTech existing, JobListingTech updated)
    {



    }

}
