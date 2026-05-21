package com.example.JobListing.Entities;

import com.example.JobListing.Entities.Enums.TechCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tech extends BaseEntity
{

    private String Name;
    private TechCategory TechCategory;

    @OneToMany(mappedBy = "Tech", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<JobListingTech> Listings = new HashSet<>();

}
