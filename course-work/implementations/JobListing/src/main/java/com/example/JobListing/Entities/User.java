package com.example.JobListing.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    public User() { }

    public String Username;
    public String Email;
    public String Password;

}
