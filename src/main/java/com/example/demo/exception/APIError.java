package com.example.demo.exception;

import java.time.Instant;

public class APIError {
    private int status;
    private String message;
    private Instant timestamp;

    public APIError(int status,String message){
         this.status =status;
         this.message = message;
         this.timestamp = Instant.now();

    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}




