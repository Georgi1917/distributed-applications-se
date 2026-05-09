package com.example.JobListing.Service.Implementation;

import com.example.JobListing.Entities.User;
import com.example.JobListing.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class UserService extends BaseService<User>
{

    private final PasswordEncoder _encoder;

    public UserService(UserRepository repository, PasswordEncoder encoder)
    {

        super(repository);
        _encoder = encoder;

    }

    @Override
    public CompletableFuture<User> Save(User entity)
    {

        entity.Password = _encoder.encode(entity.Password);

        return super.Save(entity);

    }

    @Override
    protected void UpdateEntity(User existing, User updated)
    {

        existing.Email = updated.Email;
        existing.Username = updated.Username;
        existing.Password = updated.Password;

    }
}
