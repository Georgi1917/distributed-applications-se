package com.example.JobListing.Controller;

import com.example.JobListing.Infrastructure.RequestDTOs.JobListingDTOs.JobListingCreateDTO;
import com.example.JobListing.Infrastructure.RequestDTOs.JobListingDTOs.JobListingUpdateDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.JobListingResponseDTO;
import com.example.JobListing.Service.Implementation.JobListingService;
import jakarta.validation.Valid;
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
    public List<JobListingResponseDTO> GetAllListings()
    {

        return _service.GetAllListings().join();

    }

    @GetMapping("/{id}")
    public JobListingResponseDTO GetListing(@PathVariable int id)
    {

        return _service.GetListing(id).join();

    }

    @PostMapping("/")
    public JobListingResponseDTO SaveListing(@Valid @RequestBody JobListingCreateDTO entity)
    {

        return _service.SaveListing(entity).join();

    }

    @PutMapping("/update/{id}")
    public JobListingResponseDTO UpdateListing
            (@PathVariable int id, @Valid @RequestBody JobListingUpdateDTO entity)
    {

        return _service.UpdateListing(id, entity).join();

    }

    @DeleteMapping("/delete/{id}")
    public JobListingResponseDTO DeleteListing(@PathVariable int id)
    {

        return _service.DeleteListing(id).join();

    }

}
