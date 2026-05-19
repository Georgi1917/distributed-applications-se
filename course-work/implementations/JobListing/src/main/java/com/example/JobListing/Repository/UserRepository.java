package com.example.JobListing.Repository;

import com.example.JobListing.Entities.User;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository extends IBaseRepository<User>
{
    Optional<User> findByUsername(String username);
}
