package com.example.JobListing.Controller;

import com.example.JobListing.Infrastructure.RequestDTOs.UserDTOs.UpdateDTO;
import com.example.JobListing.Infrastructure.RequestDTOs.UserDTOs.UserCreationDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.UserResponseDTO;
import com.example.JobListing.Service.Implementation.UserService;
import jakarta.validation.Valid;
import org.springframework.scheduling.annotation.Async;
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

        System.out.println(auth.getAuthorities());

        return _service.GetAllUsers().join();

    }

    @GetMapping("/test")
    public String Test(Authentication auth)
    {

        System.out.println("AUTH : " + auth);
        System.out.println("IS_AUTH : " + auth.isAuthenticated());
        System.out.println("ROLES : " + auth.getAuthorities());

        return "OK";

    }

    @GetMapping("/{id}")
    @Async
    public CompletableFuture<UserResponseDTO> GetUser(@PathVariable int id)
    {

        return _service.GetUser(id);

    }

    @PostMapping("/")
    @Async
    public CompletableFuture<UserResponseDTO> SaveUser(@Valid @RequestBody UserCreationDTO user)
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
