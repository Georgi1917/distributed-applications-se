package com.example.JobListing.Controller;

import com.example.JobListing.Infrastructure.RequestDTOs.JobApplicationDTOs.JobApplicationRequestDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.JobApplicationResponseDTO;
import com.example.JobListing.Service.Implementation.JobApplicationService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/job_application")
public class JobApplicationController
{

    private final JobApplicationService _service;

    public JobApplicationController(JobApplicationService service)
    {

        _service = service;

    }

    @GetMapping("by_user/{user_id}")
    @Async
    public CompletableFuture<List<JobApplicationResponseDTO>> GetApplicationsByUser(@PathVariable int user_id)
    {

        return _service.GetApplicationsByUser(user_id);

    }

    @GetMapping("by_listing/{listing_id}")
    @Async
    public CompletableFuture<List<JobApplicationResponseDTO>> GetApplicationsByListing(@PathVariable int listing_id)
    {

        return _service.GetApplicationsByListing(listing_id);

    }

    @GetMapping("{id}")
    @Async
    public CompletableFuture<JobApplicationResponseDTO> GetApplication(@PathVariable int id)
    {

        return _service.GetApplication(id);

    }

    @PostMapping("/")
    @Async
    public CompletableFuture<JobApplicationResponseDTO> SaveApplication(@RequestBody JobApplicationRequestDTO entity)
    {

        return _service.SaveApplication(entity);

    }

    @DeleteMapping("/delete/{id}")
    @Async
    public CompletableFuture<JobApplicationResponseDTO> DeleteApplication(@PathVariable int id)
    {

        return _service.DeleteApplication(id);

    }

}
