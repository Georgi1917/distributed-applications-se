package com.example.JobListing.Service.Implementation;

import com.example.JobListing.Entities.Company;
import com.example.JobListing.Infrastructure.RequestDTOs.CompanyDTOs.CompanyRequestDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.CompanyResponseDTO;
import com.example.JobListing.Repository.CompanyRepository;
import com.example.JobListing.Service.Interface.ICompanyService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class CompanyService extends BaseService<Company> implements ICompanyService
{

    public CompanyService(CompanyRepository repository)
    {

        super(repository);

    }

    @Async
    public CompletableFuture<List<CompanyResponseDTO>> GetAllCompanies()
    {

        return super.GetAll().thenApply(
                items -> items.stream()
                        .map(item -> CompanyResponseDTO.builder()
                                .Id(item.getId())
                                .CompanyName(item.getCompanyName())
                                .Description(item.getDescription())
                                .EmployeeCount(item.getEmployeeCount())
                                .Type(item.getType())
                                .CompanyRemotePolicy(item.getCompanyRemotePolicy())
                                .IsHiring(item.isIsHiring()).build()).toList()
        );

    }

    @Async
    public CompletableFuture<CompanyResponseDTO> GetCompany(int id)
    {

        return super.GetItem(id).thenApply(
                company -> CompanyResponseDTO.builder()
                        .Id(company.getId())
                        .CompanyName(company.getCompanyName())
                        .Description(company.getDescription())
                        .EmployeeCount(company.getEmployeeCount())
                        .Type(company.getType())
                        .CompanyRemotePolicy(company.getCompanyRemotePolicy())
                        .IsHiring(company.isIsHiring()).build()
        );

    }

    @Async
    public CompletableFuture<CompanyResponseDTO> SaveCompany(CompanyRequestDTO entity)
    {

        Company company = Company.builder()
                                .CompanyName(entity.CompanyName())
                                .Description(entity.Description())
                                .EmployeeCount(entity.EmployeeCount())
                                .Type(entity.Type())
                                .CompanyRemotePolicy(entity.CompanyRemotePolicy())
                                .IsHiring(entity.IsHiring()).build();

        super.Save(company);

        return CompletableFuture.completedFuture(CompanyResponseDTO.builder()
                .Id(company.getId())
                .CompanyName(company.getCompanyName())
                .Description(company.getDescription())
                .EmployeeCount(company.getEmployeeCount())
                .Type(company.getType())
                .CompanyRemotePolicy(company.getCompanyRemotePolicy())
                .IsHiring(company.isIsHiring()).build());

    }

    @Async
    public CompletableFuture<CompanyResponseDTO> UpdateCompany(int id, CompanyRequestDTO entity)
    {

        return super.GetItem(id).thenApply(
                company -> {

                    company.setCompanyName(entity.CompanyName());
                    company.setDescription(entity.Description());
                    company.setEmployeeCount(entity.EmployeeCount());
                    company.setType(entity.Type());
                    company.setCompanyRemotePolicy(entity.CompanyRemotePolicy());
                    company.setIsHiring(entity.IsHiring());

                    super.Save(company);
                    return company;

                }
        ).thenApply(
                company -> CompanyResponseDTO.builder()
                        .Id(company.getId())
                        .CompanyName(company.getCompanyName())
                        .Description(company.getDescription())
                        .EmployeeCount(company.getEmployeeCount())
                        .Type(company.getType())
                        .CompanyRemotePolicy(company.getCompanyRemotePolicy())
                        .IsHiring(company.isIsHiring()).build()
        );

    }

    @Async
    public CompletableFuture<CompanyResponseDTO> DeleteCompany(int id)
    {

        return super.Delete(id).thenApply(
                company -> CompanyResponseDTO.builder()
                        .Id(company.getId())
                        .CompanyName(company.getCompanyName())
                        .Description(company.getDescription())
                        .EmployeeCount(company.getEmployeeCount())
                        .Type(company.getType())
                        .CompanyRemotePolicy(company.getCompanyRemotePolicy())
                        .IsHiring(company.isIsHiring()).build()
        );

    }

    @Override
    protected void UpdateEntity(Company existing, Company updated)
    {

    }

}
