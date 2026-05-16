package com.example.JobListing.Service.Implementation;

import com.example.JobListing.Entities.Tech;
import com.example.JobListing.Repository.TechRepository;
import org.springframework.stereotype.Service;

@Service
public class TechService extends BaseService<Tech>
{

    public TechService(TechRepository repository)
    {

        super(repository);

    }

    @Override
    protected void UpdateEntity(Tech existing, Tech updated)
    {

        existing.setName(updated.getName());
        existing.setTechCategory(updated.getTechCategory());

    }

}
