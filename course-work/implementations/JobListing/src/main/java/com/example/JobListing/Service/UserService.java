package com.example.JobListing.Service;

import com.example.JobListing.Entities.User;
import com.example.JobListing.Repository.UserRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class UserService {

    private final UserRepository _repository;

    public UserService(UserRepository repository)
    {

        _repository = repository;

    }

    @Async
    public CompletableFuture<Optional<User>> GetUser(Integer id)
    {

        Optional<User> user = _repository.findById(id);

        return CompletableFuture.completedFuture(user);

    }

    @Async
    public CompletableFuture<User> Save(User user)
    {

        User saved_user = _repository.save(user);

        return CompletableFuture.completedFuture(saved_user);

    }

    @Async
    public CompletableFuture<Optional<User>> Delete(int id)
    {

        Optional<User> to_delete = _repository.findById(id);

        to_delete.ifPresent(_repository::delete);

        return CompletableFuture.completedFuture(to_delete);

    }

    @Async
    public CompletableFuture<Optional<User>> Update(int id, User user)
    {

        Optional<User> to_update = _repository.findById(id);

        to_update.ifPresent(u -> {
            u.Username = user.Username;
            u.Email = user.Email;
            u.Password = user.Password;
        });

        to_update.ifPresent(_repository::save);

        return CompletableFuture.completedFuture(to_update);

    }

}
