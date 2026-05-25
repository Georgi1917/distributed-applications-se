package com.example.JobListing.Repository;

import com.example.JobListing.Entities.Tech;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

}
