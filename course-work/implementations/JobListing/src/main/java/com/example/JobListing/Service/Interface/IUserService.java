package com.example.JobListing.Service.Interface;

import com.example.JobListing.Infrastructure.RequestDTOs.UserDTOs.RegisterDTO;
import com.example.JobListing.Infrastructure.RequestDTOs.UserDTOs.UpdateDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.CompanyResponseDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.UserResponseDTO;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IUserService
{

    @Async
    CompletableFuture<List<UserResponseDTO>> GetAllUsers();
    @Async
    CompletableFuture<UserResponseDTO> GetUser(int id);
    @Async
    CompletableFuture<UserResponseDTO> SaveUser(RegisterDTO entity);
    @Async
    CompletableFuture<UserResponseDTO> UpdateUser(int id, UpdateDTO entity);
    @Async
    CompletableFuture<UserResponseDTO> DeleteUser(int id);

}
