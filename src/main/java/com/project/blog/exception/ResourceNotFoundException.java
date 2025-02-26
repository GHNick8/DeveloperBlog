package com.project.blog.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    // Adding constructor ResourceNotFoundException 
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
