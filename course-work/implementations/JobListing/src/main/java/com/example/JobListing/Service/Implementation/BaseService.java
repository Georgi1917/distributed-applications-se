package com.example.JobListing.Service.Implementation;

import com.example.JobListing.Entities.BaseEntity;
import com.example.JobListing.Entities.User;
import com.example.JobListing.Repository.IBaseRepository;
import com.example.JobListing.Service.Interface.IService;
import org.springframework.scheduling.annotation.Async;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public abstract class BaseService<T extends BaseEntity> implements IService<T>
{

    private final IBaseRepository<T> _repository;

    public BaseService(IBaseRepository<T> repository)
    {

        _repository = repository;

    }

    @Async
    public CompletableFuture<Optional<T>> GetItem(Integer id)
    {

        Optional<T> item = _repository.findById(id);

        return CompletableFuture.completedFuture(item);

    }

    @Async
    public CompletableFuture<T> Save(T entity)
    {

        T saved_entity = _repository.save(entity);

        return CompletableFuture.completedFuture(saved_entity);

    }

    @Async
    public CompletableFuture<Optional<T>> Delete(int id)
    {

        Optional<T> to_delete = _repository.findById(id);

        to_delete.ifPresent(_repository::delete);

        return CompletableFuture.completedFuture(to_delete);

    }

    @Async
    public CompletableFuture<Optional<T>> Update(int id, T entity)
    {

        Optional<T> existing_entity = _repository.findById(id);

        existing_entity.ifPresent(t ->
            {
                UpdateEntity(t, entity);
                _repository.save(existing_entity.get());
            }
        );


        return CompletableFuture.completedFuture(existing_entity);

    }

    protected abstract void UpdateEntity(T existing, T updated);

}
