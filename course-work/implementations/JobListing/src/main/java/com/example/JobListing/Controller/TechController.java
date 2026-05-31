package com.example.JobListing.Controller;

import com.example.JobListing.Entities.Tech;
import com.example.JobListing.Infrastructure.RequestDTOs.TechDTOs.TechRequestDto;
import com.example.JobListing.Infrastructure.ResponseDTOs.TechResponseDTO;
import com.example.JobListing.Service.Implementation.TechService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("tech/")
public class TechController
{

    private final TechService _service;

    public TechController(TechService service)
    {

        _service = service;

    }

    @GetMapping("/")
    public Page<TechResponseDTO> GetAll(
            @RequestParam(required = false) String searchBy,
            @RequestParam(required = false) Long listing_id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean asc
    )
    {

        Sort sort = asc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        searchBy = (searchBy == null) ? "" : searchBy;

        if (listing_id != null)
        {
            return _service.GetTechsByListing(pageable, searchBy, listing_id).join();
        }

        return _service.GetTechsBySearchParam(pageable, searchBy).join();

    }

    @GetMapping("/{id}")
    public TechResponseDTO GetTech(@PathVariable long id)
    {

        return _service.GetTech(id).join();

    }

    @PostMapping("/")
    public TechResponseDTO SaveTech(@Valid @RequestBody TechRequestDto entity)
    {

        return _service.SaveTech(entity).join();

    }

    @PutMapping("/update/{id}")
    public TechResponseDTO UpdateTech
            (@PathVariable long id, @Valid @RequestBody TechRequestDto entity)
    {

        return _service.UpdateTech(id, entity).join();

    }

    @DeleteMapping("/delete/{id}")
    public void DeleteTech(@PathVariable long id)
    {

        _service.Delete(id);

    }

}
