package com.example.JobListing.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"user_id", "job_listing_id"}
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobApplication extends BaseEntity
{

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User Applicant;

    @ManyToOne
    @JoinColumn(name = "job_listing_id")
    private JobListing JobListing;

}
