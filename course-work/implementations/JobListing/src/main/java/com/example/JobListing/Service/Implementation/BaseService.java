package com.example.JobListing.Service.Implementation;

import com.example.JobListing.Entities.BaseEntity;
import com.example.JobListing.Repository.IBaseRepository;
import com.example.JobListing.Service.Interface.IService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class BaseService<T extends BaseEntity> implements IService<T>
{

    private final IBaseRepository<T> _repository;

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
    public CompletableFuture<T> GetItem(Integer id)
    {

        T item = _repository.findById(id).orElseThrow();

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

        T to_delete = _repository.findById(id).orElseThrow();

        _repository.delete(to_delete);

        return CompletableFuture.completedFuture(to_delete);

    }

    @Async
    public CompletableFuture<T> Update(int id, T entity)
    {

        T existing_entity = _repository.findById(id).orElseThrow();

        UpdateEntity(existing_entity, entity);
        _repository.save(existing_entity);

        return CompletableFuture.completedFuture(existing_entity);

    }

    protected abstract void UpdateEntity(T existing, T updated);

}
