package com.example.JobListing.Service.Interface;

import com.example.JobListing.Entities.Tech;
import com.example.JobListing.Infrastructure.RequestDTOs.TechDTOs.TechRequestDto;
import com.example.JobListing.Infrastructure.ResponseDTOs.TechResponseDTO;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface ITechService
{

    @Async
    CompletableFuture<Page<TechResponseDTO>> GetTechsBySearchParam
            (Pageable pageable, @Nullable String searchBy);

    @Async
    CompletableFuture<Page<TechResponseDTO>> GetTechsByListing
            (Pageable pageable, @Nullable String searchBy, long listing_id);

    @Async
    CompletableFuture<TechResponseDTO> GetTech(long id);
    @Async
    CompletableFuture<TechResponseDTO> SaveTech(TechRequestDto entity);
    @Async
    CompletableFuture<TechResponseDTO> UpdateTech(long id, TechRequestDto entity);



}
