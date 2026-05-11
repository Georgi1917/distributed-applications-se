package com.example.JobListing.Service.Implementation;

import com.example.JobListing.Entities.User;
import com.example.JobListing.Infrastructure.RequestDTOs.UserDTOs.RegisterDTO;
import com.example.JobListing.Infrastructure.RequestDTOs.UserDTOs.UpdateDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.UserResponseDTO;
import com.example.JobListing.Repository.UserRepository;
import org.springframework.scheduling.annotation.Async;
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

    @Async
    public CompletableFuture<UserResponseDTO> GetUser(int id)
    {

        CompletableFuture<User> future = super.GetItem(id);
        User user = future.join();

        return CompletableFuture.completedFuture(UserResponseDTO.builder()
                                                    .Id(user.getId())
                                                    .Username(user.getUsername())
                                                    .Email(user.getEmail()).build());

    }

    @Async
    public CompletableFuture<UserResponseDTO> SaveUser(RegisterDTO entity)
    {

        User user = User.builder()
                        .Username(entity.Username())
                        .Email(entity.Email())
                        .Password(_encoder.encode(entity.Password())).build();

        super.Save(user);

        return CompletableFuture.completedFuture(UserResponseDTO.builder()
                                                      .Id(user.getId())
                                                      .Username(user.getUsername())
                                                      .Email(user.getEmail()).build());

    }

    @Async
    public CompletableFuture<UserResponseDTO> UpdateUser(int id, UpdateDTO entity)
    {

        CompletableFuture<User> future = super.GetItem(id);
        User user = future.join();

        user.setUsername(entity.Username());
        user.setEmail(entity.Email());

        super.Save(user);

        return CompletableFuture.completedFuture(UserResponseDTO.builder()
                .Id(user.getId())
                .Username(user.getUsername())
                .Email(user.getEmail()).build());

    }

    @Async
    public CompletableFuture<UserResponseDTO> DeleteUser(int id)
    {

        CompletableFuture<User> future = super.Delete(id);
        User user = future.join();

        return CompletableFuture.completedFuture(UserResponseDTO.builder()
                .Id(user.getId())
                .Username(user.getUsername())
                .Email(user.getEmail()).build());

    }

    @Override
    protected void UpdateEntity(User existing, User updated)
    {

        existing.setEmail(updated.getEmail());
        existing.setUsername(updated.getUsername());
        existing.setPassword(updated.getPassword());

    }
}
