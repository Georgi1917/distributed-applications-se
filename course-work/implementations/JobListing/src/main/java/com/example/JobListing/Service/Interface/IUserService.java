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
    public CompletableFuture<List<UserResponseDTO>> GetAllUsers();
    @Async
    public CompletableFuture<UserResponseDTO> GetUser(int id);
    @Async
    public CompletableFuture<UserResponseDTO> SaveUser(RegisterDTO entity);
    @Async
    public CompletableFuture<UserResponseDTO> UpdateUser(int id, UpdateDTO entity);
    @Async
    public CompletableFuture<UserResponseDTO> DeleteUser(int id);

}
