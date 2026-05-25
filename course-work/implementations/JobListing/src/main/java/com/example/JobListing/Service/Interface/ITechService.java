package com.example.JobListing.Service.Interface;

import com.example.JobListing.Entities.Tech;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface ITechService
{

    @Async
    public CompletableFuture<Page<Tech>> GetTechsBySearchParam
            (Pageable pageable, @Nullable String searchBy);

}
