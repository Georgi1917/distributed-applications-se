package com.example.JobListing.Controller;

import com.example.JobListing.Entities.Tech;
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
    public Page<Tech> GetAll(
            @RequestParam(required = false) String searchBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean asc
    )
    {

        Sort sort = asc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        searchBy = (searchBy == null) ? "" : searchBy;

        return _service.GetTechsBySearchParam(pageable, searchBy).join();

    }

    @GetMapping("/{id}")
    public Tech GetTech(@PathVariable int id)
    {

        return _service.GetItem(id).join();

    }

    @PostMapping("/")
    public Tech SaveTech(@Valid @RequestBody Tech entity)
    {

        return _service.Save(entity).join();

    }

    @PutMapping("/update/{id}")
    public Tech UpdateTech(@PathVariable int id, @Valid @RequestBody Tech entity)
    {

        return _service.Update(id, entity).join();

    }

    @DeleteMapping("/delete/{id}")
    public void DeleteTech(@PathVariable int id)
    {

        _service.Delete(id);

    }

}
