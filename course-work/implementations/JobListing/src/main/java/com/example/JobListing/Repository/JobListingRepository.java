package com.example.JobListing.Repository;

import com.example.JobListing.Entities.Company;
import com.example.JobListing.Entities.JobListing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface JobListingRepository extends IBaseRepository<JobListing>
{

    @Query("""
        SELECT DISTINCT l
        FROM JobListing l
        JOIN l.techs lt
        WHERE lt.tech.Id = :tech_id
    """)
    Page<JobListing> findByJobListingTech_Tech_Id(
            Integer tech_id,
            Pageable pageable
    );

    @Query("""
        SELECT DISTINCT l
        FROM JobListing l
        JOIN l.applications app
        WHERE app.applicant.Id = :user_id
    """)
    Page<JobListing> findByUser_User_Id(
            Integer user_id,
            Pageable pageable
    );

}
