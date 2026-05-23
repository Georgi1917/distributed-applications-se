package com.example.JobListing.Controller;

import com.example.JobListing.Infrastructure.RequestDTOs.JobListingTechDTOs.JobListingTechRequestDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.JobListingTechResponseDTO;
import com.example.JobListing.Service.Implementation.JobListingTechService;
import jakarta.validation.Valid;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("job_listing_tech")
public class JobListingTechController
{

    private final JobListingTechService _service;

    public JobListingTechController(JobListingTechService service)
    {

        _service = service;

    }

    @GetMapping("by_listing/{listing_id}")
    public List<JobListingTechResponseDTO> GetAllByListing(@PathVariable int listing_id)
    {

        return _service.GetAllByListingId(listing_id).join();

    }

    @GetMapping("by_tech/{tech_id}")
    public List<JobListingTechResponseDTO> GetAllByTech(@PathVariable int tech_id)
    {

        return _service.GetAllByTechId(tech_id).join();

    }

    @PostMapping("/")
    public JobListingTechResponseDTO SaveJobListingTech
            (@Valid @RequestBody JobListingTechRequestDTO entity)
    {

        return _service.SaveListingTech(entity).join();

    }

    @DeleteMapping("/delete/{id}")
    public void DeleteJobListingTech(@PathVariable int id)
    {

        _service.DeleteListingTech(id);

    }

}
