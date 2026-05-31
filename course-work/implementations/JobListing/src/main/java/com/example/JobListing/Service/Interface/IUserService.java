package com.example.JobListing.Service.Interface;

import com.example.JobListing.Infrastructure.RequestDTOs.UserDTOs.UpdateDTO;
import com.example.JobListing.Infrastructure.RequestDTOs.UserDTOs.UserCreationDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.UserResponseDTO;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IUserService
{

    @Async
    CompletableFuture<Page<UserResponseDTO>> GetAllUsers(Pageable pageable, @Nullable String searchBy);
    @Async
    CompletableFuture<Page<UserResponseDTO>> GetUsersByListing
            (Pageable pageable, long listing_id, @Nullable String searchBy);
    @Async
    CompletableFuture<UserResponseDTO> GetUser(long id);
    @Async
    CompletableFuture<UserResponseDTO> GetUserByUsername(String username);
    @Async
    CompletableFuture<UserResponseDTO> SaveUser(UserCreationDTO entity);
    @Async
    CompletableFuture<UserResponseDTO> UpdateUser(long id, UpdateDTO entity);
    @Async
    CompletableFuture<UserResponseDTO> DeleteUser(long id);

}
