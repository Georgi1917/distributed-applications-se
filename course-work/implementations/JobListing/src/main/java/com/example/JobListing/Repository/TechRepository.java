package com.example.JobListing.Repository;

import com.example.JobListing.Entities.Tech;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TechRepository extends IBaseRepository<Tech>
{

    @Query("""
        SELECT DISTINCT t
        FROM Tech t
        WHERE (:searchBy IS NULL 
            OR LOWER(t.name) LIKE LOWER(CONCAT("%", :searchBy, "%"))
            OR LOWER(t.techCategory) LIKE LOWER(CONCAT("%", :searchBy, "%")))
    """)
    Page<Tech> findBySearchParam
            (Pageable pageable, @Param("searchBy") @Nullable String searchBy);

    @Query("""
        SELECT DISTINCT t
        FROM Tech t
        JOIN t.listings lis
        WHERE lis.listing.Id = :listing_id
            AND (:searchBy IS NULL 
            OR LOWER(t.name) LIKE LOWER(CONCAT("%", :searchBy, "%"))
            OR LOWER(t.techCategory) LIKE LOWER(CONCAT("%", :searchBy, "%")))
    """)
    Page<Tech> findByListing
            (Pageable pageable, @Param("searchBy") @Nullable String searchBy,
             @Param("listing_id") int listing_id);

    Optional<Tech> findByName(String name);

    @Query("""
        SELECT DISTINCT t
        FROM Tech t
        WHERE t.Id != :id AND t.name = :name  
    """)
    Optional<Tech> findByNameWithoutId(int id, String name);
}
