package com.example.JobListing.Controller;

import com.example.JobListing.Entities.User;
import com.example.JobListing.Service.UserService;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.json.JsonMapper;

import java.util.Optional;

@RestController
@RequestMapping("user/")
public class UserController {

    private final UserService _service;

    public UserController(UserService service)
    {
        _service = service;
    }

    @GetMapping("/{id}")
    public Optional<User> GetUser(@PathVariable int id)
    {

        return _service.GetUser(id);

    }

    @PostMapping("/")
    public User SaveUser(@RequestBody User user)
    {

        System.out.println(user.Email);
        System.out.println(user.Username);
        System.out.println(user.Password);
        return _service.Save(user);

    }

}
