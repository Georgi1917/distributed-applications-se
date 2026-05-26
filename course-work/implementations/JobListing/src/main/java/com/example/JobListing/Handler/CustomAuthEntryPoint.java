package com.example.JobListing.Handler;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import org.springframework.security.core.AuthenticationException;
import java.io.IOException;

@Component
public class CustomAuthEntryPoint implements AuthenticationEntryPoint
{

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException ex
    ) throws IOException, ServletException
    {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        response.getWriter().write(
                    """
                {
                    "error": "Unauthorized",
                    "message": "%s"
                }
            """.formatted(ex.getMessage()));

    }
}
