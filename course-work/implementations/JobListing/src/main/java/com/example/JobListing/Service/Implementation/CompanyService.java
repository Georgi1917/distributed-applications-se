package com.example.JobListing.Service.Implementation;

import com.example.JobListing.Entities.Company;
import com.example.JobListing.Entities.User;
import com.example.JobListing.Infrastructure.RequestDTOs.CompanyDTOs.CompanyRequestDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.CompanyResponseDTO;
import com.example.JobListing.Repository.CompanyRepository;
import com.example.JobListing.Repository.IBaseRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class CompanyService extends BaseService<Company>
{

    public CompanyService(CompanyRepository repository)
    {

        super(repository);

    }

    @Async
    public CompletableFuture<CompanyResponseDTO> GetCompany(int id)
    {

        CompletableFuture<Company> future = super.GetItem(id);
        Company company = future.join();

        return CompletableFuture.completedFuture(CompanyResponseDTO.builder()
                .Id(company.getId())
                .CompanyName(company.getCompanyName())
                .Description(company.getDescription())
                .EmployeeCount(company.getEmployeeCount())
                .Type(company.getType())
                .RemotePolicy(company.getRemotePolicy())
                .IsHiring(company.isIsHiring()).build());

    }

    @Async
    public CompletableFuture<CompanyResponseDTO> SaveCompany(CompanyRequestDTO entity)
    {

        Company company = Company.builder()
                                .CompanyName(entity.CompanyName())
                                .Description(entity.Description())
                                .EmployeeCount(entity.EmployeeCount())
                                .Type(entity.Type())
                                .RemotePolicy(entity.RemotePolicy())
                                .IsHiring(entity.IsHiring()).build();

        super.Save(company);

        return CompletableFuture.completedFuture(CompanyResponseDTO.builder()
                .Id(company.getId())
                .CompanyName(company.getCompanyName())
                .Description(company.getDescription())
                .EmployeeCount(company.getEmployeeCount())
                .Type(company.getType())
                .RemotePolicy(company.getRemotePolicy())
                .IsHiring(company.isIsHiring()).build());

    }

    @Async
    public CompletableFuture<CompanyResponseDTO> UpdateCompany(int id, CompanyRequestDTO entity)
    {

        CompletableFuture<Company> future = super.GetItem(id);
        Company company = future.join();

        company.setCompanyName(entity.CompanyName());
        company.setDescription(entity.Description());
        company.setEmployeeCount(entity.EmployeeCount());
        company.setType(entity.Type());
        company.setRemotePolicy(entity.RemotePolicy());
        company.setIsHiring(entity.IsHiring());

        super.Save(company);

        return CompletableFuture.completedFuture(CompanyResponseDTO.builder()
                .Id(company.getId())
                .CompanyName(company.getCompanyName())
                .Description(company.getDescription())
                .EmployeeCount(company.getEmployeeCount())
                .Type(company.getType())
                .RemotePolicy(company.getRemotePolicy())
                .IsHiring(company.isIsHiring()).build());

    }

    @Async
    public CompletableFuture<CompanyResponseDTO> DeleteCompany(int id)
    {

        CompletableFuture<Company> future = super.Delete(id);
        Company company = future.join();

        return CompletableFuture.completedFuture(CompanyResponseDTO.builder()
                .Id(company.getId())
                .CompanyName(company.getCompanyName())
                .Description(company.getDescription())
                .EmployeeCount(company.getEmployeeCount())
                .Type(company.getType())
                .RemotePolicy(company.getRemotePolicy())
                .IsHiring(company.isIsHiring()).build());

    }

    @Override
    protected void UpdateEntity(Company existing, Company updated)
    {

    }

}
