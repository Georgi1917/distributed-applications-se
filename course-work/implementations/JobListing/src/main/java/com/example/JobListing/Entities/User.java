package com.example.JobListing.Entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    private String Username;
    private String Email;
    private String Password;

    @OneToMany(mappedBy = "Applicant", cascade = CascadeType.ALL)
    private Set<JobApplication> Applications = new HashSet<>();

}
