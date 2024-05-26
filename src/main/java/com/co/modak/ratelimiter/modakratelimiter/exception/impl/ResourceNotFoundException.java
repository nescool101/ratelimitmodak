package com.co.modak.ratelimiter.modakratelimiter.exception.impl;

public class ResourceNotFoundException  extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}