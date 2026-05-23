package com.example.JobListing.Controller;

import com.example.JobListing.Infrastructure.RequestDTOs.CompanyDTOs.CompanyRequestDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.CompanyResponseDTO;
import com.example.JobListing.Service.Implementation.CompanyService;
import jakarta.validation.Valid;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/")
    public List<CompanyResponseDTO> GetAllCompanies()
    {

        return _service.GetAllCompanies().join();

    }

    @GetMapping("/{id}")
    public CompanyResponseDTO GetCompany(@PathVariable int id)
    {

        return _service.GetCompany(id).join();

    }

    @PostMapping("/")
    public CompanyResponseDTO SaveCompany(@Valid @RequestBody CompanyRequestDTO entity)
    {

        return _service.SaveCompany(entity).join();

    }

    @PutMapping("/update/{id}")
    public CompanyResponseDTO UpdateCompany
                (@PathVariable int id, @Valid @RequestBody CompanyRequestDTO entity)
    {

        return _service.UpdateCompany(id, entity).join();

    }

    @DeleteMapping("/delete/{id}")
    public CompanyResponseDTO DeleteCompany(@PathVariable int id)
    {

        return _service.DeleteCompany(id).join();

    }

}
