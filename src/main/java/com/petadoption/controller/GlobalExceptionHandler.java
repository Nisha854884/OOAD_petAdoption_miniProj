package com.petadoption.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    // public String handleException(Exception e) {
    //     return "Error: " + e.getMessage();
    // }
    @ResponseBody  // add this
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(500).body("Error: " + e.getMessage());
    }
}