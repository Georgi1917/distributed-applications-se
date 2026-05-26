package com.example.JobListing.Service.Implementation;

import com.example.JobListing.Entities.Company;
import com.example.JobListing.Exception.ItemAlreadyExists;
import com.example.JobListing.Infrastructure.RequestDTOs.CompanyDTOs.CompanyRequestDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.CompanyResponseDTO;
import com.example.JobListing.Repository.CompanyRepository;
import com.example.JobListing.Service.Interface.ICompanyService;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class CompanyService extends BaseService<Company> implements ICompanyService
{

    private final CompanyRepository _repository;

    public CompanyService(CompanyRepository repository)
    {

        super(repository);
        _repository = repository;

    }

    @Async
    public CompletableFuture<Page<CompanyResponseDTO>> GetAllCompanies
            (Pageable pageable, @Nullable String searchBy)
    {

        return CompletableFuture.completedFuture(_repository.findBySearchParameter(pageable, searchBy))
                .thenApply(
                page -> page.map(
                        item -> CompanyResponseDTO.builder()
                                .Id(item.getId())
                                .CompanyName(item.getCompanyName())
                                .Description(item.getDescription())
                                .EmployeeCount(item.getEmployeeCount())
                                .Type(item.getType())
                                .CompanyRemotePolicy(item.getCompanyRemotePolicy())
                                .IsHiring(item.isHiring()).build())
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
                        .IsHiring(company.isHiring()).build()
        );

    }

    @Async
    public CompletableFuture<CompanyResponseDTO> SaveCompany(CompanyRequestDTO entity)
    {

        if (_repository.findBycompanyName(entity.CompanyName()).isPresent())
        {
            throw new ItemAlreadyExists("Company with that name already exists.");
        }

        Company company = Company.builder()
                                .companyName(entity.CompanyName())
                                .description(entity.Description())
                                .employeeCount(entity.EmployeeCount())
                                .type(entity.Type())
                                .companyRemotePolicy(entity.CompanyRemotePolicy())
                                .isHiring(entity.IsHiring()).build();

        super.Save(company);

        return CompletableFuture.completedFuture(CompanyResponseDTO.builder()
                .Id(company.getId())
                .CompanyName(company.getCompanyName())
                .Description(company.getDescription())
                .EmployeeCount(company.getEmployeeCount())
                .Type(company.getType())
                .CompanyRemotePolicy(company.getCompanyRemotePolicy())
                .IsHiring(company.isHiring()).build());

    }

    @Async
    public CompletableFuture<CompanyResponseDTO> UpdateCompany(int id, CompanyRequestDTO entity)
    {

        if (_repository.findByNameWithoutId(entity.CompanyName(), id).isPresent())
        {
            throw new ItemAlreadyExists("Company with that name already exists!");
        }

        return super.GetItem(id).thenApply(
                company -> {

                    company.setCompanyName(entity.CompanyName());
                    company.setDescription(entity.Description());
                    company.setEmployeeCount(entity.EmployeeCount());
                    company.setType(entity.Type());
                    company.setCompanyRemotePolicy(entity.CompanyRemotePolicy());
                    company.setHiring(entity.IsHiring());

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
                        .IsHiring(company.isHiring()).build()
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
                        .IsHiring(company.isHiring()).build()
        );

    }

}
