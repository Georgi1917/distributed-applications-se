package com.example.JobListing.Controller;

import com.example.JobListing.Entities.Tech;
import com.example.JobListing.Service.Implementation.TechService;
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
    @Async
    public CompletableFuture<List<Tech>> GetAll()
    {

        return _service.GetAll();

    }

    @GetMapping("/{id}")
    @Async
    public CompletableFuture<Tech> GetTech(@PathVariable int id)
    {

        return _service.GetItem(id);

    }

    @PostMapping("/")
    @Async
    public CompletableFuture<Tech> SaveTech(@RequestBody Tech entity)
    {

        return _service.Save(entity);

    }

    @PutMapping("/update/{id}")
    @Async
    public CompletableFuture<Tech> UpdateTech(@PathVariable int id, @RequestBody Tech entity)
    {

        return _service.Update(id, entity);

    }

    @DeleteMapping("/delete/{id}")
    @Async
    public void DeleteTech(@PathVariable int id)
    {

        _service.Delete(id);

    }

}
