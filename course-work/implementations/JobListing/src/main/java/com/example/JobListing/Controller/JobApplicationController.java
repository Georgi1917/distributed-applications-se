package com.example.JobListing.Controller;

import com.example.JobListing.Entities.User;
import com.example.JobListing.Exception.InvalidRequest;
import com.example.JobListing.Infrastructure.RequestDTOs.JobApplicationDTOs.JobApplicationRequestDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.JobApplicationResponseDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.UserResponseDTO;
import com.example.JobListing.Service.Implementation.JobApplicationService;
import com.example.JobListing.Service.Implementation.UserService;
import jakarta.validation.Valid;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/job_application")
public class JobApplicationController
{

    private final JobApplicationService _service;
    private final UserService _user_service;

    public JobApplicationController
            (JobApplicationService service, UserService user_service)
    {

        _service = service;
        _user_service = user_service;

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
    public JobApplicationResponseDTO SaveApplication
            (@Valid @RequestBody JobApplicationRequestDTO entity, Authentication auth)
    {

        String logged_username = auth.getName();
        String logged_role = auth.getAuthorities().toArray()[0].toString();

        UserResponseDTO user_to_apply = _user_service.GetUser(entity.user_id()).join();

        if (!logged_username.contentEquals(user_to_apply.Username())
                && logged_role.contentEquals("ROLE_USER"))
        {
            throw new InvalidRequest("Invalid request. User submitted for application is not the one logged in.");
        }

        return _service.SaveApplication(entity).join();

    }

    @DeleteMapping("/delete/{id}")
    public JobApplicationResponseDTO DeleteApplication(@PathVariable int id)
    {

        return _service.DeleteApplication(id).join();

    }

}
