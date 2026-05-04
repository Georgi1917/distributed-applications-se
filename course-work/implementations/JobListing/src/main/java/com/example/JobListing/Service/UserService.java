package com.example.JobListing.Service;

import com.example.JobListing.Entities.User;
import com.example.JobListing.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository _repository;

    public UserService(UserRepository repository)
    {

        _repository = repository;

    }

    public Optional<User> GetUser(Integer id)
    {

        return _repository.findById(id);

    }

    public User Save(User user)
    {

        return _repository.save(user);

    }

}
