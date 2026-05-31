package com.example.JobListing.Service.Interface;

import com.example.JobListing.Entities.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IService<T extends BaseEntity>
{

    @Async
    CompletableFuture<List<T>> GetAll();
    @Async
    CompletableFuture<Page<T>> GetAllPageable(Pageable pageable);
    @Async
    CompletableFuture<T> GetItem(long id);
    @Async
    CompletableFuture<T> Save(T entity);
    @Async
    CompletableFuture<T> Delete(long id);

}
