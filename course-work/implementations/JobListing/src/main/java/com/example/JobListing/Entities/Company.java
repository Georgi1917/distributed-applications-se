package com.example.JobListing.Entities;

import com.example.JobListing.Entities.Enums.CompanyType;
import com.example.JobListing.Entities.Enums.CompanyRemotePolicy;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company extends BaseEntity
{

    @Column(nullable = false, unique = true)
    private String companyName;
    @Column(nullable = false)
    private String description;
    private Integer employeeCount;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CompanyType type;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CompanyRemotePolicy companyRemotePolicy;
    @Column(nullable = false)
    private boolean isHiring;

    @OneToMany(
            mappedBy = "company",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<JobListing> jobListings = new ArrayList<>();

}
