package com.example.JobListing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class JobListingApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobListingApplication.class, args);
	}

}
