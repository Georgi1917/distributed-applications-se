package com.example.JobListing.Service.Implementation;

import com.example.JobListing.Entities.BaseEntity;
import com.example.JobListing.Exception.ElementNotFound;
import com.example.JobListing.Repository.IBaseRepository;
import com.example.JobListing.Service.Interface.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class BaseService<T extends BaseEntity> implements IService<T>
{

    protected final IBaseRepository<T> _repository;

    public BaseService(IBaseRepository<T> repository)
    {

        _repository = repository;

    }

    @Async
    public CompletableFuture<List<T>> GetAll()
    {

        return CompletableFuture.completedFuture((List<T>) _repository.findAll());

    }

    @Async
    public CompletableFuture<Page<T>> GetAllPageable(Pageable pageable)
    {

        return CompletableFuture.completedFuture(_repository.findAll(pageable));

    }

    @Async
    public CompletableFuture<T> GetItem(Integer id)
    {

        T item = _repository.findById(id).orElseThrow(
                () -> new ElementNotFound("Item Not Found.")
        );

        return CompletableFuture.completedFuture(item);

    }

    @Async
    public CompletableFuture<T> Save(T entity)
    {

        T saved_entity = _repository.save(entity);

        return CompletableFuture.completedFuture(saved_entity);

    }

    @Async
    public CompletableFuture<T> Delete(int id)
    {

        T to_delete = _repository.findById(id).orElseThrow(
                () -> new ElementNotFound("Item Not Found.")
        );

        _repository.delete(to_delete);

        return CompletableFuture.completedFuture(to_delete);

    }

}
