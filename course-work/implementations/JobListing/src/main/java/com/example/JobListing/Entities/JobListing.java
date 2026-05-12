package com.example.JobListing.Entities;

import jakarta.persistence.Entity;
import lombok.*;
import tools.jackson.core.ObjectReadContext;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobListing extends BaseEntity
{

    private String Name;
    private String Description;


}
