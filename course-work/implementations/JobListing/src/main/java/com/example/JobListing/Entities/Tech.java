package com.example.JobListing.Entities;

import com.example.JobListing.Entities.Enums.TechCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @Column(nullable = false, unique = true)
    @NotBlank
    private String Name;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private TechCategory TechCategory;

    @OneToMany(mappedBy = "Tech", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<JobListingTech> Listings = new HashSet<>();

}
