package com.example.JobListing.Service.Interface;

import com.example.JobListing.Infrastructure.RequestDTOs.CompanyDTOs.CompanyRequestDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.CompanyResponseDTO;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ICompanyService
{

    @Async
    CompletableFuture<Page<CompanyResponseDTO>> GetAllCompanies
            (Pageable pageable, @Nullable String searchBy);
    @Async
    CompletableFuture<CompanyResponseDTO> GetCompany(int id);
    @Async
    CompletableFuture<CompanyResponseDTO> SaveCompany(CompanyRequestDTO entity);
    @Async
    CompletableFuture<CompanyResponseDTO> UpdateCompany(int id, CompanyRequestDTO entity);
    @Async
    CompletableFuture<CompanyResponseDTO> DeleteCompany(int id);

}
