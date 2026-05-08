package com.example.JobListing.Repository;

import com.example.JobListing.Entities.BaseEntity;
import org.springframework.data.repository.CrudRepository;

public interface IBaseRepository<T extends BaseEntity> extends CrudRepository<T, Integer> { }
