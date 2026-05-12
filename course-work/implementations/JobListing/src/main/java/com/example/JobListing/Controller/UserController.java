package com.example.JobListing.Controller;

import com.example.JobListing.Infrastructure.RequestDTOs.UserDTOs.RegisterDTO;
import com.example.JobListing.Infrastructure.RequestDTOs.UserDTOs.UpdateDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.UserResponseDTO;
import com.example.JobListing.Service.Implementation.UserService;
import jakarta.validation.Valid;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("user/")
public class UserController {

    private final UserService _service;

    public UserController(UserService service)
    {
        _service = service;
    }

    @GetMapping("/")
    @Async
    public CompletableFuture<List<UserResponseDTO>> GetAllUsers()
    {

        return _service.GetAllUsers();

    }

    @GetMapping("/{id}")
    @Async
    public CompletableFuture<UserResponseDTO> GetUser(@PathVariable int id)
    {

        return _service.GetUser(id);

    }

    @PostMapping("/")
    @Async
    public CompletableFuture<UserResponseDTO> SaveUser(@Valid @RequestBody RegisterDTO user)
    {

        return _service.SaveUser(user);

    }

    @PutMapping("/update/{id}")
    @Async
    public CompletableFuture<UserResponseDTO> UpdateUser(@PathVariable int id, @Valid @RequestBody UpdateDTO entity)
    {

        return _service.UpdateUser(id, entity);

    }

    @DeleteMapping("/delete/{id}")
    @Async
    public CompletableFuture<UserResponseDTO> DeleteUser(@PathVariable int id)
    {

        return _service.DeleteUser(id);

    }

}
