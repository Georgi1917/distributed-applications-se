package com.example.JobListing.Controller;

import com.example.JobListing.Infrastructure.RequestDTOs.JobListingDTOs.JobListingCreateDTO;
import com.example.JobListing.Infrastructure.RequestDTOs.JobListingDTOs.JobListingUpdateDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.JobListingResponseDTO;
import com.example.JobListing.Service.Implementation.JobListingService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Page<JobListingResponseDTO> GetAllListings(
            @RequestParam(required = false) Integer tech_id,
            @RequestParam(required = false) Integer user_id,
            @RequestParam(required = false) Integer company_id,
            @RequestParam(required = false) String searchBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean asc
    )
    {

        Sort sort = asc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        searchBy = (searchBy == null) ? "" : searchBy;

        if (user_id != null)
        {
            return _service.GetListingsByUser(pageable, user_id, searchBy).join();
        }

        if (tech_id != null)
        {
            return _service.GetListingsByTech(pageable, tech_id, searchBy).join();
        }

        if(company_id != null)
        {
            return _service.GetListingsByCompany(pageable, company_id, searchBy).join();
        }

        return _service.GetAllListings(pageable, searchBy).join();

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
