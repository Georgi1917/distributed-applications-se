package com.example.JobListing.Controller;

import com.example.JobListing.Entities.User;
import com.example.JobListing.Infrastructure.RequestDTOs.RegisterDTO;
import com.example.JobListing.Service.Implementation.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
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
    public CompletableFuture<Optional<User>> GetUser(@PathVariable int id)
    {

        return _service.GetItem(id);

    }

    @PostMapping("/")
    public CompletableFuture<User> SaveUser(@RequestBody RegisterDTO user)
    {

        User entity = new User();
        entity.Username = user.Username;
        entity.Email = user.Email;
        entity.Password = user.Password;

        return _service.Save(entity);

    }

    @PutMapping("/update/{id}")
    public CompletableFuture<Optional<User>> UpdateUser(@PathVariable int id, @RequestBody User user)
    {

        return _service.Update(id, user);

    }

    @DeleteMapping("/delete/{id}")
    public CompletableFuture<Optional<User>> DeleteUser(@PathVariable int id)
    {

        return _service.Delete(id);

    }

}
