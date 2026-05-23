package com.example.JobListing.Controller;

import com.example.JobListing.Entities.Tech;
import com.example.JobListing.Service.Implementation.TechService;
import jakarta.validation.Valid;
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
    public List<Tech> GetAll()
    {

        return _service.GetAll().join();

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
