package com.example.JobListing.Repository;

import com.example.JobListing.Entities.User;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends IBaseRepository<User>
{
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    @Query("""
        SELECT DISTINCT u
        FROM User u
        JOIN u.applications app
        WHERE app.jobListing.Id = :listing_id
            AND (:searchBy IS NULL
                OR LOWER(u.username) LIKE LOWER(CONCAT("%", :searchBy, "%"))
                OR LOWER(u.email) LIKE LOWER(CONCAT("%", :searchBy, "%"))
                OR LOWER(u.role) LIKE LOWER(CONCAT("%", :searchBy, "%")))
    """)
    Page<User> findByListing
            (Pageable pageable, @Param("listing_id") int listing_id, @Param("searchBy") @Nullable String searchBy);

    @Query("""
        SELECT DISTINCT u
        FROM User u
        WHERE (:searchBy IS NULL 
            OR LOWER(u.username) LIKE LOWER(CONCAT("%", :searchBy, "%"))
            OR LOWER(u.email) LIKE LOWER(CONCAT("%", :searchBy, "%"))
            OR LOWER(u.role) LIKE LOWER(CONCAT("%", :searchBy, "%")))
    """)
    Page<User> findBySearchParam(Pageable pageable, @Param("searchBy") @Nullable String searchBy);

    @Query("""
        SELECT DISTINCT u
        FROM User u
        WHERE u.Id != :id AND u.username = :username
    """)
    Optional<User> findByUsernameWithoutId(String username, int id);

    @Query("""
        SELECT DISTINCT u
        FROM User u
        WHERE u.Id != :id AND u.email = :email
    """)
    Optional<User> findByEmailWithoutId(String email, int id);
}
