package com.example.JobListing.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    public User() { }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer Id;
    public String Username;
    public String Email;
    public String Password;

}
