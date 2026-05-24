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
    private String name;
    @Column(nullable = false)
    private String description;
    @Enumerated(EnumType.STRING)
    private JobListingExperienceLevel experienceLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "jobListing", cascade = CascadeType.ALL)
    private Set<JobApplication> applications = new HashSet<>();

    @OneToMany(mappedBy = "listing", cascade = CascadeType.ALL)
    private Set<JobListingTech> techs = new HashSet<>();

}
