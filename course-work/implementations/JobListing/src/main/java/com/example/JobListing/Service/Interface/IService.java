package com.example.JobListing.Service.Interface;

import com.example.JobListing.Entities.BaseEntity;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface IService<T extends BaseEntity>
{

    @Async
    public CompletableFuture<T> GetItem(Integer id);
    @Async
    public CompletableFuture<T> Save(T entity);
    @Async
    public CompletableFuture<T> Delete(int id);
    @Async
    public CompletableFuture<T> Update(int id, T user);

}
