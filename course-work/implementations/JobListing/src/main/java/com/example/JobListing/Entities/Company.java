package com.example.JobListing.Entities;

import com.example.JobListing.Entities.Enums.CompanyType;
import com.example.JobListing.Entities.Enums.CompanyRemotePolicy;
import jakarta.persistence.*;
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
    private String CompanyName;
    @Column(nullable = false)
    private String Description;
    private Integer EmployeeCount;
    @Enumerated(EnumType.STRING)
    private CompanyType Type;
    @Enumerated(EnumType.STRING)
    private CompanyRemotePolicy CompanyRemotePolicy;
    private boolean IsHiring;

    @OneToMany(
            mappedBy = "Company",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<JobListing> JobListings = new ArrayList<>();

}
