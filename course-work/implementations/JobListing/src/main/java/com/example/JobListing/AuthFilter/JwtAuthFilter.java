package com.example.JobListing.AuthFilter;

import com.example.JobListing.AuthService.JwtService;
import com.example.JobListing.Entities.User;
import com.example.JobListing.Repository.UserRepository;
import com.example.JobListing.Service.Implementation.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter
{

    private final JwtService _service;
    private final UserRepository _repository;

    public JwtAuthFilter
            (JwtService service,
             UserRepository repository)
    {

        _service = service;
        _repository = repository;

    }

    @Override
    protected void doFilterInternal
            (HttpServletRequest request,
             HttpServletResponse response,
             FilterChain filterChain)
            throws ServletException, IOException
    {

        System.out.println("Hello from filter");

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer "))
        {

            System.out.println("1");
            filterChain.doFilter(request, response);
            return;

        }

        String token = authHeader.substring(7);

        if (!_service.IsTokenValid(token))
        {

            System.out.println("2");
            filterChain.doFilter(request, response);
            return;

        }

        String username = _service.ExtractUsername(token);

        User user = _repository.findByUsername(username).get();

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );

        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext()
                .setAuthentication(auth);

        System.out.println("3");
        filterChain.doFilter(request, response);

    }

}
