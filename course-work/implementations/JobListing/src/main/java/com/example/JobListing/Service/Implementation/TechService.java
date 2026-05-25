package com.example.JobListing.Service.Implementation;

import com.example.JobListing.Entities.Tech;
import com.example.JobListing.Repository.TechRepository;
import com.example.JobListing.Service.Interface.ITechService;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class TechService extends BaseService<Tech> implements ITechService
{

    private final TechRepository _repository;

    public TechService(TechRepository repository)
    {

        super(repository);
        _repository = repository;

    }

    @Async
    public CompletableFuture<Page<Tech>> GetTechsBySearchParam
            (Pageable pageable, @Nullable String searchBy)
    {

        return CompletableFuture.completedFuture(_repository.findBySearchParam(pageable, searchBy));

    }

    @Override
    protected void UpdateEntity(Tech existing, Tech updated)
    {

        existing.setName(updated.getName());
        existing.setTechCategory(updated.getTechCategory());

    }

}
