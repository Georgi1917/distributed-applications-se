package com.example.JobListing.Service.Interface;

import com.example.JobListing.Entities.BaseEntity;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IService<T extends BaseEntity>
{

    @Async
    CompletableFuture<List<T>> GetAll();
    @Async
    CompletableFuture<T> GetItem(Integer id);
    @Async
    CompletableFuture<T> Save(T entity);
    @Async
    CompletableFuture<T> Delete(int id);
    @Async
    CompletableFuture<T> Update(int id, T user);

}
