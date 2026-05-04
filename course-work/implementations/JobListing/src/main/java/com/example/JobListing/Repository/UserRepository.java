package com.example.JobListing.Repository;

import com.example.JobListing.Entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
