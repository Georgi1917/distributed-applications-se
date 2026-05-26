package com.example.JobListing.Controller;

import com.example.JobListing.AuthService.JwtService;
import com.example.JobListing.Entities.Enums.UserRole;
import com.example.JobListing.Exception.InvalidLogin;
import com.example.JobListing.Infrastructure.RequestDTOs.UserDTOs.LoginDTO;
import com.example.JobListing.Infrastructure.RequestDTOs.UserDTOs.RegisterDTO;
import com.example.JobListing.Infrastructure.RequestDTOs.UserDTOs.UserCreationDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.AuthResponseDTO;
import com.example.JobListing.Infrastructure.ResponseDTOs.UserResponseDTO;
import com.example.JobListing.Service.Implementation.UserService;
import jakarta.validation.Valid;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/auth")
public class AuthController
{

    private final AuthenticationManager authManager;
    private final JwtService _service;
    private final UserService _user_service;

    public AuthController
            (AuthenticationManager manager, JwtService service,
             UserService user_service)
    {

        authManager = manager;
        _service = service;
        _user_service = user_service;

    }

    @PostMapping("/login")
    public AuthResponseDTO login(@Valid @RequestBody LoginDTO entity)
    {

        System.out.println("Controller 1");

        try{

            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            entity.username(),
                            entity.password()
                    )
            );

            System.out.println("Controller 2");

            String token = _service.GenerateToken(entity.username());

            System.out.println("Controller 3");

            return AuthResponseDTO.builder().token(token).build();

        } catch (BadCredentialsException | UsernameNotFoundException e)
        {
            throw new InvalidLogin("Invalid username or password!");
        }

    }

    @PostMapping("/register")
    @Async
    public CompletableFuture<UserResponseDTO> register(@Valid @RequestBody RegisterDTO entity)
    {

        return _user_service.SaveUser(UserCreationDTO.builder()
                .username(entity.username())
                .email(entity.email())
                .password(entity.password())
                .role(UserRole.USER).build());

    }

}
