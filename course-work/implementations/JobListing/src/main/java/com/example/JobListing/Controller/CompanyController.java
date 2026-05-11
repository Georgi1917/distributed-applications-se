package com.example.JobListing.Controller;

import com.example.JobListing.Infrastructure.RequestDTOs.CompanyDTOs.CompanyRequestDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.CompanyResponseDTO;
import com.example.JobListing.Service.Implementation.CompanyService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("company/")
public class CompanyController
{

    private final CompanyService _service;

    public CompanyController(CompanyService service)
    {

        _service = service;

    }

    @GetMapping("/{id}")
    @Async
    public CompletableFuture<CompanyResponseDTO> GetCompany(@PathVariable int id)
    {

        return _service.GetCompany(id);

    }

    @PostMapping("/")
    @Async
    public CompletableFuture<CompanyResponseDTO> SaveCompany(@RequestBody CompanyRequestDTO entity)
    {

        return _service.SaveCompany(entity);

    }

    @PutMapping("/update/{id}")
    @Async
    public CompletableFuture<CompanyResponseDTO> UpdateCompany
                (@PathVariable int id, @RequestBody CompanyRequestDTO entity)
    {

        return _service.UpdateCompany(id, entity);

    }

    @DeleteMapping("/delete/{id}")
    @Async
    public CompletableFuture<CompanyResponseDTO> DeleteCompany(@PathVariable int id)
    {

        return _service.DeleteCompany(id);

    }

}
