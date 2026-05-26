package com.example.JobListing.Repository;

import com.example.JobListing.Entities.Company;
import com.example.JobListing.Entities.JobListing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JobListingRepository extends IBaseRepository<JobListing>
{

    @Query("""
        SELECT DISTINCT l
        FROM JobListing l
        JOIN l.techs lt
        WHERE lt.tech.Id = :tech_id
            AND (:searchBy IS NULL
                OR LOWER (l.name) LIKE LOWER(CONCAT("%", :searchBy, "%"))
                OR LOWER(l.description) LIKE LOWER(CONCAT("%", :searchBy, "%"))
                OR LOWER(l.experienceLevel) LIKE LOWER(CONCAT("%", :searchBy, "%")))
    """)
    Page<JobListing> findByJobListingTech_Tech_Id(
            @Param("tech_id") Integer tech_id,
            @Param("searchBy") String searchBy,
            Pageable pageable
    );

    @Query("""
        SELECT DISTINCT l
        FROM JobListing l
        JOIN l.applications app
        WHERE app.applicant.Id = :user_id
            AND (:searchBy IS NULL
                OR LOWER (l.name) LIKE LOWER(CONCAT("%", :searchBy, "%"))
                OR LOWER(l.description) LIKE LOWER(CONCAT("%", :searchBy, "%"))
                OR LOWER(l.experienceLevel) LIKE LOWER(CONCAT("%", :searchBy, "%")))
    """)
    Page<JobListing> findByUser_User_Id(
            @Param("user_id") Integer user_id,
            @Param("searchBy") String searchBy,
            Pageable pageable
    );

    @Query("""
        SELECT DISTINCT l
        FROM JobListing l
        WHERE (:searchBy IS NULL
                OR LOWER (l.name) LIKE LOWER(CONCAT("%", :searchBy, "%"))
                OR LOWER(l.description) LIKE LOWER(CONCAT("%", :searchBy, "%"))
                OR LOWER(l.experienceLevel) LIKE LOWER(CONCAT("%", :searchBy, "%")))
    """)
    Page<JobListing> findBySearchParam(
            @Param("searchBy") String searchBy,
            Pageable pageable
    );

    @Query("""
        SELECT DISTINCT l
        FROM JobListing l
        JOIN l.company c
        WHERE c.Id = :company_id
            AND (:searchBy IS NULL
                OR LOWER (l.name) LIKE LOWER(CONCAT("%", :searchBy, "%"))
                OR LOWER(l.description) LIKE LOWER(CONCAT("%", :searchBy, "%"))
                OR LOWER(l.experienceLevel) LIKE LOWER(CONCAT("%", :searchBy, "%")))
    """)
    Page<JobListing> findByCompany(
            @Param("company_id") int company_id,
            @Param("searchBy") String searchBy,
            Pageable pageable
    );

}
