package com.example.JobListing.Controller;

import com.example.JobListing.Infrastructure.RequestDTOs.UserDTOs.UpdateDTO;
import com.example.JobListing.Infrastructure.RequestDTOs.UserDTOs.UserCreationDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.UserResponseDTO;
import com.example.JobListing.Service.Implementation.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
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
    public List<UserResponseDTO> GetAllUsers(Authentication auth)
    {

        return _service.GetAllUsers().join();

    }

    @GetMapping("/{id}")
    public UserResponseDTO GetUser(@PathVariable int id)
    {

        return _service.GetUser(id).join();

    }

    @PostMapping("/")
    public UserResponseDTO SaveUser(@Valid @RequestBody UserCreationDTO user)
    {

        return _service.SaveUser(user).join();

    }

    @PutMapping("/update/{id}")
    public UserResponseDTO UpdateUser(@PathVariable int id, @Valid @RequestBody UpdateDTO entity)
    {

        return _service.UpdateUser(id, entity).join();

    }

    @DeleteMapping("/delete/{id}")
    public UserResponseDTO DeleteUser(@PathVariable int id)
    {

        return _service.DeleteUser(id).join();

    }

}
