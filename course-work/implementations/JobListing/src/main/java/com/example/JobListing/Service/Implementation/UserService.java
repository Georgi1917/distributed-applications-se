package com.example.JobListing.Service.Implementation;

import com.example.JobListing.Entities.User;
import com.example.JobListing.Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService<User>
{

    public UserService(UserRepository repository)
    {

        super(repository);

    }

    @Override
    protected void UpdateEntity(User existing, User updated)
    {

        existing.Email = updated.Email;
        existing.Username = updated.Username;
        existing.Password = updated.Password;

    }
}
