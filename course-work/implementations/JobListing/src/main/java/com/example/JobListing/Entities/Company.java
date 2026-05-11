package com.example.JobListing.Entities;

import com.example.JobListing.Entities.Enums.CompanyType;
import com.example.JobListing.Entities.Enums.RemotePolicy;
import jakarta.persistence.Entity;
import lombok.*;

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

}
