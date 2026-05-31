package com.example.JobListing.Controller;

import com.example.JobListing.Infrastructure.RequestDTOs.CompanyDTOs.CompanyRequestDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.CompanyResponseDTO;
import com.example.JobListing.Service.Implementation.CompanyService;
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
@RequestMapping("company/")
public class CompanyController
{

    private final CompanyService _service;

    public CompanyController(CompanyService service)
    {

        _service = service;

    }

    @GetMapping("/")
    public Page<CompanyResponseDTO> GetAllCompanies(
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

        return _service.GetAllCompanies(pageable, searchBy).join();

    }

    @GetMapping("/{id}")
    public CompanyResponseDTO GetCompany(@PathVariable long id)
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
                (@PathVariable long id, @Valid @RequestBody CompanyRequestDTO entity)
    {

        return _service.UpdateCompany(id, entity).join();

    }

    @DeleteMapping("/delete/{id}")
    public CompanyResponseDTO DeleteCompany(@PathVariable long id)
    {

        return _service.DeleteCompany(id).join();

    }

}
