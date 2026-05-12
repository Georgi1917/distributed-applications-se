package com.example.JobListing.Entities;

import com.example.JobListing.Entities.Enums.CompanyType;
import com.example.JobListing.Entities.Enums.RemotePolicy;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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

    private String CompanyName;
    private String Description;
    private Integer EmployeeCount;
    private CompanyType Type;
    private RemotePolicy RemotePolicy;
    private boolean IsHiring;

    @OneToMany(
            mappedBy = "Company",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<JobListing> JobListings = new ArrayList<>();

}
