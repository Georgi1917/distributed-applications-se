package com.example.JobListing.Entities;

import com.example.JobListing.Entities.Enums.TechCategory;
import jakarta.persistence.Entity;
import lombok.*;

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

}
