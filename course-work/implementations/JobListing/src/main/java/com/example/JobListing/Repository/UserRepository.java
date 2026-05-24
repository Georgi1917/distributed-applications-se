package com.example.JobListing.Repository;

import com.example.JobListing.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends IBaseRepository<User>
{
    Optional<User> findByUsername(String username);

    @Query("""
        SELECT DISTINCT u
        FROM User u
        JOIN u.applications app
        WHERE app.jobListing.Id = :listing_id
    """)
    Page<User> findByListing(Pageable pageable, int listing_id);
}
