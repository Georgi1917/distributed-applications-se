package com.example.JobListing.Controller;

import com.example.JobListing.Infrastructure.RequestDTOs.UserDTOs.RegisterDTO;
import com.example.JobListing.Infrastructure.RequestDTOs.UserDTOs.UpdateDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.UserResponseDTO;
import com.example.JobListing.Service.Implementation.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("user/")
public class UserController {

    private final UserService _service;

    public UserController(UserService service)
    {
        _service = service;
    }

    @GetMapping("/{id}")
    public CompletableFuture<UserResponseDTO> GetUser(@PathVariable int id)
    {

        return _service.GetUser(id);

    }

    @PostMapping("/")
    public CompletableFuture<UserResponseDTO> SaveUser(@RequestBody RegisterDTO user)
    {

        return _service.SaveUser(user);

    }

    @PutMapping("/update/{id}")
    public CompletableFuture<UserResponseDTO> UpdateUser(@PathVariable int id, @RequestBody UpdateDTO entity)
    {

        return _service.UpdateUser(id, entity);

    }

    @DeleteMapping("/delete/{id}")
    public CompletableFuture<UserResponseDTO> DeleteUser(@PathVariable int id)
    {

        return _service.DeleteUser(id);

    }

}
