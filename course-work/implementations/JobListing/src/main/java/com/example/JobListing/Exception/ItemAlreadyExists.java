package com.example.JobListing.Exception;

public class ItemAlreadyExists extends RuntimeException {
    public ItemAlreadyExists(String message) {
        super(message);
    }
}
