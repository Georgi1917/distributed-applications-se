package com.example.JobListing.Controller;

import com.example.JobListing.Infrastructure.RequestDTOs.JobApplicationDTOs.JobApplicationRequestDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.JobApplicationResponseDTO;
import com.example.JobListing.Service.Implementation.JobApplicationService;
import jakarta.validation.Valid;
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
    public List<JobApplicationResponseDTO> GetApplicationsByUser(@PathVariable int user_id)
    {

        return _service.GetApplicationsByUser(user_id).join();

    }

    @GetMapping("by_listing/{listing_id}")
    public List<JobApplicationResponseDTO> GetApplicationsByListing(@PathVariable int listing_id)
    {

        return _service.GetApplicationsByListing(listing_id).join();

    }

    @GetMapping("{id}")
    public JobApplicationResponseDTO GetApplication(@PathVariable int id)
    {

        return _service.GetApplication(id).join();

    }

    @PostMapping("/")
    public JobApplicationResponseDTO SaveApplication(@Valid @RequestBody JobApplicationRequestDTO entity)
    {

        return _service.SaveApplication(entity).join();

    }

    @DeleteMapping("/delete/{id}")
    public JobApplicationResponseDTO DeleteApplication(@PathVariable int id)
    {

        return _service.DeleteApplication(id).join();

    }

}
