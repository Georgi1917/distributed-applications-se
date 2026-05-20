package com.example.JobListing.Service.Implementation;

import com.example.JobListing.Entities.User;
import com.example.JobListing.Infrastructure.RequestDTOs.UserDTOs.UpdateDTO;
import com.example.JobListing.Infrastructure.RequestDTOs.UserDTOs.UserCreationDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.UserResponseDTO;
import com.example.JobListing.Repository.UserRepository;
import com.example.JobListing.Service.Interface.IUserService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class UserService extends BaseService<User> implements IUserService
{

    private final PasswordEncoder _encoder;

    public UserService(UserRepository repository, PasswordEncoder encoder)
    {

        super(repository);
        _encoder = encoder;

    }

    @Async
    public CompletableFuture<List<UserResponseDTO>> GetAllUsers()
    {

        CompletableFuture<List<User>> future = super.GetAll();
        List<User> items = future.join();

        return CompletableFuture.completedFuture(items.stream()
                                            .map(item ->
                                                UserResponseDTO.builder()
                                                .Id(item.getId())
                                                .Username(item.getUsername())
                                                .Email(item.getEmail())
                                                .Role(item.getRole()).build()
                                                ).toList());

    }

    @Async
    public CompletableFuture<UserResponseDTO> GetUser(int id)
    {

        CompletableFuture<User> future = super.GetItem(id);
        User user = future.join();

        return CompletableFuture.completedFuture(UserResponseDTO.builder()
                .Id(user.getId())
                .Username(user.getUsername())
                .Email(user.getEmail())
                .Role(user.getRole())
                .build());

    }

    @Async
    public CompletableFuture<UserResponseDTO> SaveUser(UserCreationDTO entity)
    {

        User user = User.builder()
                        .username(entity.username())
                        .email(entity.email())
                        .password(_encoder.encode(entity.password()))
                        .role(entity.role()).build();

        super.Save(user);

        return CompletableFuture.completedFuture(UserResponseDTO.builder()
                .Id(user.getId())
                .Username(user.getUsername())
                .Email(user.getEmail())
                .Role(user.getRole())
                .build());

    }

    @Async
    public CompletableFuture<UserResponseDTO> UpdateUser(int id, UpdateDTO entity)
    {

        CompletableFuture<User> future = super.GetItem(id);
        User user = future.join();

        user.setUsername(entity.username());
        user.setEmail(entity.email());
        user.setRole(entity.role());

        super.Save(user);

        return CompletableFuture.completedFuture(UserResponseDTO.builder()
                .Id(user.getId())
                .Username(user.getUsername())
                .Email(user.getEmail())
                .Role(user.getRole())
                .build());

    }

    @Async
    public CompletableFuture<UserResponseDTO> DeleteUser(int id)
    {

        CompletableFuture<User> future = super.Delete(id);
        User user = future.join();

        return CompletableFuture.completedFuture(UserResponseDTO.builder()
                .Id(user.getId())
                .Username(user.getUsername())
                .Email(user.getEmail())
                .Role(user.getRole())
                .build());

    }

    @Override
    protected void UpdateEntity(User existing, User updated)
    {

    }
}
