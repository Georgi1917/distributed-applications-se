package com.example.JobListing.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"listing_id", "tech_id"}
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobListingTech extends BaseEntity
{

    @ManyToOne
    @JoinColumn(name = "listing_id")
    private JobListing Listing;

    @ManyToOne
    @JoinColumn(name = "tech_id")
    private Tech Tech;

    private boolean IsRequired;

}
