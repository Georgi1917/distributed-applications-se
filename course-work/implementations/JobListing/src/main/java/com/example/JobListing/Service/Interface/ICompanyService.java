package com.example.JobListing.Service.Interface;

import com.example.JobListing.Infrastructure.RequestDTOs.CompanyDTOs.CompanyRequestDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.CompanyResponseDTO;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ICompanyService
{

    @Async
    public CompletableFuture<List<CompanyResponseDTO>> GetAllCompanies();
    @Async
    public CompletableFuture<CompanyResponseDTO> GetCompany(int id);
    @Async
    public CompletableFuture<CompanyResponseDTO> SaveCompany(CompanyRequestDTO entity);
    @Async
    public CompletableFuture<CompanyResponseDTO> UpdateCompany(int id, CompanyRequestDTO entity);
    @Async
    public CompletableFuture<CompanyResponseDTO> DeleteCompany(int id);

}
