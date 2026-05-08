package com.example.JobListing.Service.Interface;

import com.example.JobListing.Entities.BaseEntity;
import com.example.JobListing.Entities.User;
import org.springframework.scheduling.annotation.Async;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface IService<T extends BaseEntity>
{

    @Async
    public CompletableFuture<Optional<T>> GetItem(Integer id);
    @Async
    public CompletableFuture<T> Save(T entity);
    @Async
    public CompletableFuture<Optional<T>> Delete(int id);
    @Async
    public CompletableFuture<Optional<T>> Update(int id, T user);

}
