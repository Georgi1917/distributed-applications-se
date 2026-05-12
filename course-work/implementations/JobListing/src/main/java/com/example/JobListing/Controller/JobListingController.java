package com.example.JobListing.Controller;

import com.example.JobListing.Infrastructure.RequestDTOs.JobListingDTOs.JobListingCreateDTO;
import com.example.JobListing.Infrastructure.RequestDTOs.JobListingDTOs.JobListingUpdateDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.JobListingResponseDTO;
import com.example.JobListing.Service.Implementation.JobListingService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/job_listing")
public class JobListingController
{

    private final JobListingService _service;

    public JobListingController(JobListingService service)
    {

        _service = service;

    }

    @GetMapping("/")
    @Async
    public CompletableFuture<List<JobListingResponseDTO>> GetAllListings()
    {

        return _service.GetAllListings();

    }

    @GetMapping("/{id}")
    @Async
    public CompletableFuture<JobListingResponseDTO> GetListing(@PathVariable int id)
    {

        return _service.GetListing(id);

    }

    @PostMapping("/")
    @Async
    public CompletableFuture<JobListingResponseDTO> SaveListing(@RequestBody JobListingCreateDTO entity)
    {

        return _service.SaveListing(entity);

    }

    @PutMapping("/update/{id}")
    @Async
    public CompletableFuture<JobListingResponseDTO> UpdateListing
            (@PathVariable int id, @RequestBody JobListingUpdateDTO entity)
    {

        return _service.UpdateListing(id, entity);

    }

    @DeleteMapping("/delete/{id}")
    @Async
    public CompletableFuture<JobListingResponseDTO> DeleteListing(@PathVariable int id)
    {

        return _service.DeleteListing(id);

    }

}
