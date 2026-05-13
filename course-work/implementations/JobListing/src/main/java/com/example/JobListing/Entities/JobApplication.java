package com.example.JobListing.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
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
