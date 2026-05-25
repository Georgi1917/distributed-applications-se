package com.example.JobListing.Service.Implementation;

import com.example.JobListing.Entities.User;
import com.example.JobListing.Infrastructure.RequestDTOs.UserDTOs.UpdateDTO;
import com.example.JobListing.Infrastructure.RequestDTOs.UserDTOs.UserCreationDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.UserResponseDTO;
import com.example.JobListing.Repository.UserRepository;
import com.example.JobListing.Service.Interface.IUserService;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class UserService extends BaseService<User> implements IUserService
{

    private final PasswordEncoder _encoder;
    private final UserRepository user_repo;

    public UserService(UserRepository repository, PasswordEncoder encoder)
    {

        super(repository);
        user_repo = repository;
        _encoder = encoder;

    }

    @Async
    public CompletableFuture<Page<UserResponseDTO>> GetAllUsers
            (Pageable pageable, @Nullable String searchBy)
    {

        return CompletableFuture.completedFuture(user_repo.findBySearchParam(pageable, searchBy)).thenApply(
                page -> page.map(
                        item -> UserResponseDTO.builder()
                                        .Id(item.getId())
                                        .Username(item.getUsername())
                                        .Email(item.getEmail())
                                        .Role(item.getRole()).build()
                )
        );

    }

    @Async
    public CompletableFuture<Page<UserResponseDTO>> GetUsersByListing
            (Pageable pageable, int listing_id, @Nullable String searchBy)
    {

        return CompletableFuture.completedFuture(user_repo.findByListing(pageable, listing_id, searchBy).map(
                item -> UserResponseDTO.builder()
                        .Id(item.getId())
                        .Username(item.getUsername())
                        .Email(item.getEmail())
                        .Role(item.getRole()).build()
        ));

    }

    @Async
    public CompletableFuture<UserResponseDTO> GetUser(int id)
    {

        return super.GetItem(id).thenApply(
                user -> UserResponseDTO.builder()
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

        return super.GetItem(id).thenApply(
                user -> {

                    user.setUsername(entity.username());
                    user.setEmail(entity.email());
                    user.setRole(entity.role());

                    super.Save(user);

                    return user;
                }
        ).thenApply(
                user -> UserResponseDTO.builder()
                        .Id(user.getId())
                        .Username(user.getUsername())
                        .Email(user.getEmail())
                        .Role(user.getRole())
                        .build());

    }

    @Async
    public CompletableFuture<UserResponseDTO> DeleteUser(int id)
    {

        return super.Delete(id).thenApply(
                user -> UserResponseDTO.builder()
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
