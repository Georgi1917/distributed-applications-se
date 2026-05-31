package com.example.JobListing.Repository;

import com.example.JobListing.Entities.Company;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CompanyRepository extends IBaseRepository<Company>
{

    @Query("""
        SELECT DISTINCT c
        FROM Company c
        WHERE (:searchBy IS NULL 
            OR LOWER(c.companyName) LIKE LOWER(CONCAT("%", :searchBy, "%"))
            OR LOWER(c.description) LIKE LOWER(CONCAT("%", :searchBy, "%"))
            OR LOWER(c.type) LIKE LOWER(CONCAT("%", :searchBy, "%"))
            OR LOWER(c.companyRemotePolicy) LIKE LOWER(CONCAT("%", :searchBy, "%"))) 
    """)
    Page<Company> findBySearchParameter(Pageable pageable, @Param("searchBy") @Nullable String searchBy);

    @Query("""
        SELECT DISTINCT c
        FROM Company c
        WHERE c.Id != :id AND c.companyName = :companyName 
    """)
    Optional<Company> findByNameWithoutId(String companyName, long id);

    Optional<Company> findBycompanyName(String companyName);

}
