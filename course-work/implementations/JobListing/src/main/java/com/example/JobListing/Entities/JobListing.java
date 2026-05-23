package com.example.JobListing.Entities;

import com.example.JobListing.Entities.Enums.JobListingExperienceLevel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import tools.jackson.core.ObjectReadContext;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobListing extends BaseEntity
{

    @Column(nullable = false)
    private String Name;
    @Column(nullable = false)
    private String Description;
    @Enumerated(EnumType.STRING)
    private JobListingExperienceLevel ExperienceLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company Company;

    @OneToMany(mappedBy = "JobListing", cascade = CascadeType.ALL)
    private Set<JobApplication> Applications = new HashSet<>();

    @OneToMany(mappedBy = "Listing", cascade = CascadeType.ALL)
    private Set<JobListingTech> Techs = new HashSet<>();

}
