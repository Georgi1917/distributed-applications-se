package com.example.JobListing.Service.Implementation;

import com.example.JobListing.Entities.Tech;
import com.example.JobListing.Exception.ItemAlreadyExists;
import com.example.JobListing.Infrastructure.RequestDTOs.TechDTOs.TechRequestDto;
import com.example.JobListing.Infrastructure.ResponseDTOs.TechResponseDTO;
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
    public CompletableFuture<Page<TechResponseDTO>> GetTechsBySearchParam
            (Pageable pageable, @Nullable String searchBy)
    {

        return CompletableFuture.completedFuture(_repository.findBySearchParam(pageable, searchBy).map(
                item -> TechResponseDTO.builder()
                        .id(item.getId())
                        .name(item.getName())
                        .techCategory(item.getTechCategory()).build()
        ));

    }

    @Async
    public CompletableFuture<Page<TechResponseDTO>> GetTechsByListing
            (Pageable pageable, @Nullable String searchBy, int listing_id)
    {

        return CompletableFuture.completedFuture(_repository.findByListing(pageable, searchBy, listing_id).map(
                item -> TechResponseDTO.builder()
                        .id(item.getId())
                        .name(item.getName())
                        .techCategory(item.getTechCategory()).build()
        ));

    }

    @Async
    public CompletableFuture<TechResponseDTO> GetTech(int id)
    {

        return super.GetItem(id).thenApply(
                item -> TechResponseDTO.builder()
                        .id(item.getId())
                        .name(item.getName())
                        .techCategory(item.getTechCategory()).build()
        );

    }

    @Async
    public CompletableFuture<TechResponseDTO> SaveTech(TechRequestDto entity)
    {

        if (_repository.findByName(entity.name()).isPresent())
        {
            throw new ItemAlreadyExists("Tech with that name already exists");
        }

        Tech tech = Tech.builder()
                .name(entity.name())
                .techCategory(entity.techCategory()).build();

        super.Save(tech);

        return CompletableFuture.completedFuture(TechResponseDTO.builder()
                .id(tech.getId())
                .name(tech.getName())
                .techCategory(tech.getTechCategory()).build());

    }

    @Async
    public CompletableFuture<TechResponseDTO> UpdateTech(int id, TechRequestDto entity)
    {

        if (_repository.findByNameWithoutId(id, entity.name()).isPresent())
        {
            throw new ItemAlreadyExists("Tech with that name already exists");
        }

        return super.GetItem(id).thenApply(
                item -> {
                    item.setName(entity.name());
                    item.setTechCategory(entity.techCategory());

                    super.Save(item);
                    return item;

                }
        ).thenApply(
                item -> TechResponseDTO.builder()
                        .id(item.getId())
                        .name(item.getName())
                        .techCategory(item.getTechCategory()).build());

    }

}
