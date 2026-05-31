package com.example.JobListing.Config;

import com.example.JobListing.AuthFilter.JwtAuthFilter;
import com.example.JobListing.Handler.CustomAccessDeniedHandler;
import com.example.JobListing.Handler.CustomAuthEntryPoint;
import com.example.JobListing.Service.Implementation.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@Configuration
public class SecurityConfig
{

    private final JwtAuthFilter jwtAuthFilter;
    private final CustomAuthEntryPoint authEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    public SecurityConfig
            (JwtAuthFilter filter,
             CustomAuthEntryPoint authEntryPoint,
             CustomAccessDeniedHandler accessDeniedHandler)
    {

        jwtAuthFilter = filter;
        this.authEntryPoint = authEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;

    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {

        return new BCryptPasswordEncoder();

    }

    @Bean
    public AuthenticationManager authenticationManager(
            DaoAuthenticationProvider provider
    ) throws Exception
    {

        return new ProviderManager(provider);

    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            CustomUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    )
    {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);

        return provider;

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:8080", "https://editor.swagger.io", "http://127.0.0.1:8080"));
        config.setAllowedMethods(List.of("*"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            DaoAuthenticationProvider provider
    ) throws Exception
    {

        http
                .authenticationProvider(provider)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/auth/**").permitAll()

                            .requestMatchers(HttpMethod.GET, "/user/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/user/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/user/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/user/**").hasRole("ADMIN")

                            .requestMatchers(HttpMethod.GET, "/company/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/company/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/company/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/company/**").hasRole("ADMIN")

                            .requestMatchers(HttpMethod.GET, "/job_listing/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/job_listing/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/job_listing/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/job_listing/**").hasRole("ADMIN")

                            .requestMatchers(HttpMethod.GET, "/tech/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/tech/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/tech/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/tech/**").hasRole("ADMIN")

                            .requestMatchers(HttpMethod.GET, "/job_application/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/job_application/**").hasAnyRole("USER", "ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/job_application/**").hasRole("ADMIN")

                            .requestMatchers(HttpMethod.GET, "/job_listing_tech/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/job_listing_tech/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/job_listing_tech/**").hasRole("ADMIN")

                            .anyRequest().authenticated()
                )
                .addFilterBefore(
                        jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .exceptionHandling(ex ->
                        ex.authenticationEntryPoint(authEntryPoint)
                          .accessDeniedHandler(accessDeniedHandler));

        return http.build();
    }

}
